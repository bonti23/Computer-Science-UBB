$(document).ready (function() {
    $("#btn").click(function() {
        console.log("HERE")
        var nume = $('#nume').val();
        var dataNasterii = new Date($('#data_nasterii').val());
        var varsta = $('#varsta').val();
        var email = $('#email').val();

        var mesaj = "";
        // validare nume
        var ok = 1;
        if(nume.length == 0){
            ok = 0;
            mesaj += "Incomplete name!<br>";
        }
        for(let character of nume)
            if(character >= '0' && character <= '9'){
                ok = 0;
                mesaj += "Invalid name!<br>";
            }
        if(ok == 0)
            $('#nume').css('border-color', 'red');
        else
            $('#nume').css('border-color', 'black');

        // validare data
        ok = 1;
        if(!dataNasterii || isNaN(dataNasterii.getTime())){
            ok = 0;
            mesaj += "Incomplete Birthdate!<br>";
        }
        if(dataNasterii > Date.now()){
            ok = 0;
            mesaj += "Invalid Birthdate!<br>";
        }
        if(ok == 0)
            $('#data_nasterii').css('border-color', 'red');
        else
            $('#data_nasterii').css('border-color', 'black');


        // validare varsta
        ok = 1;
        if(varsta.length == 0){
            ok = 0;
            mesaj += "Incomplete age!<br>"
        }
        let gr = 1;
        for(let character of varsta)
            if(character < '0' || character > '9'){
                ok = 0;
                gr = 0;
            }
        if(gr == 0 && ok == 0)
            mesaj += "Invalid age!<br>"
        if(ok == 0)
            $('#varsta').css('border-color', 'red');
        else
            $('#varsta').css('border-color', 'black');

        // validare email
        ok = 1;
        if(email.length == 0){
            ok = 0;
            mesaj += "Incomplete email!<br>"
        }
        else if(!email.match(/^.*@.*$/g)){
            ok = 0;
            mesaj += "Invalid email<br>"
        }
        if(ok == 0)
            $('#email').css('border-color', 'red');
        else
            $('#email').css('border-color', 'black');

        if(mesaj == "")
            $('#mesaj').html("Successfully registered!");
        else
            $('#mesaj').html(mesaj);
    });

});
