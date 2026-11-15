$exe = "tema1_ppd_static.exe"

$configs = @(
    @{ N=10; M=10; n=3; m=3; threads=@(4) },
    @{ N=1000; M=1000; n=5; m=5; threads=@(2,4,8,16) },
    @{ N=10; M=10000; n=5; m=5; threads=@(2,4,8,16) },
    @{ N=10000; M=10; n=5; m=5; threads=@(2,4,8,16) },
    @{ N=10000; M=10000; n=5; m=5; threads=@(2,4,8,16) }
)

$out = "results.csv"
if (!(Test-Path $out)) {
    Set-Content $out "N,M,n,m,Threads,Mod,Timp(ms)"
}

foreach ($conf in $configs) {
    foreach ($p in $conf.threads) {
        Write-Host "---------------------------------------------"
        Write-Host "Rulare pentru N=$($conf.N) M=$($conf.M) p=$p"

        # Secvential
        $output = cmd /c ".\$exe $p 1 1 2>&1"
        $time = ($output | Select-String "Timp executie").ToString() -replace "[^\d\.]", ""
        Add-Content $out "$($conf.N),$($conf.M),$($conf.n),$($conf.m),$p,Secvential,$time"

        # Paralel
        $output = cmd /c ".\$exe $p 0 1 2>&1"
        $time = ($output | Select-String "Timp executie").ToString() -replace "[^\d\.]", ""
        Add-Content $out "$($conf.N),$($conf.M),$($conf.n),$($conf.m),$p,Paralel,$time"
    }
}
Write-Host "Testele s-au terminat. Rezultatele sunt in $out"