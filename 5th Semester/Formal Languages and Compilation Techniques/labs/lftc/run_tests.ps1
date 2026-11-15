# ==========================
# Laborator 2 - Rulare automată teste
# ==========================

$exe = "ppd2.exe"

# configurațiile de test (poți ajusta)
$configs = @(
    @{ N=10; M=10; threads=@(2) },
    @{ N=1000; M=1000; threads=@(2,4,8,16) },
    @{ N=10000; M=10000; threads=@(2,4,8,16) }
)

$out = "results.csv"
if (!(Test-Path $out)) {
    Set-Content $out "N,M,Threads,Tip,Timp(ms)"
}

foreach ($conf in $configs) {
    foreach ($p in $conf.threads) {
        Write-Host "---------------------------------------------"
        Write-Host "Rulare pentru N=$($conf.N) M=$($conf.M) p=$p"

        # (1) Generează fișier de intrare aleator pentru dimensiunea curentă
        $inputFile = "date_$($conf.N)x$($conf.M).txt"
        $f = New-Object System.IO.StreamWriter($inputFile, $false)
        $f.WriteLine("$($conf.N) $($conf.M)")

        for ($i=0; $i -lt $conf.N; $i++) {
            $row = ""
            for ($j=0; $j -lt $conf.M; $j++) {
                $row += [math]::Round((Get-Random)*10)  # valori 0–10
                if ($j -lt $conf.M-1) { $row += " " }
            }
            $f.WriteLine($row)
        }

        # Kernel simplu 3x3
        $f.WriteLine("1 0 -1")
        $f.WriteLine("1 0 -1")
        $f.WriteLine("1 0 -1")
        $f.Close()

        # (2) Copiază fișierul ca N10M10n3.txt (programul tău așa îl caută)
        Copy-Item $inputFile -Destination "N10M10n3.txt" -Force

        # (3) Rulează programul
        $output = cmd /c ".\$exe" 2>&1

        # (4) Extrage timpii din output
        $seq = ($output | Select-String "Sequential done in").ToString() -replace '[^\d\.]', ''
        $par = ($output | Select-String "Parallel" | Select-String "done in").ToString() -replace '[^\d\.]', ''

        if ($seq -and $par) {
            Add-Content $out "$($conf.N),$($conf.M),$p,Secvential,$seq"
            Add-Content $out "$($conf.N),$($conf.M),$p,Paralel,$par"
        } else {
            Write-Host "⚠️  Nu s-au putut extrage timpii pentru $($conf.N)x$($conf.M), $p threaduri"
        }
    }
}

Write-Host "`n✅ Testele s-au terminat. Rezultatele sunt salvate în $out"
