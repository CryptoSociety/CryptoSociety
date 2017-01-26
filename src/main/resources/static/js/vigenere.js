/**
 * Created by Duke on 1/20/17.
 */
'use strict';
$(document).ready(function () {
    var $plaintext = $('#vigenereplaintextBox'),
        $ciphertext = $('#vigenereciphertextBox'),
        $encrypt = $('#vigenereencrypt'),
        $decrypt = $('#vigeneredecrypt'),
        $keyword = $('#keyword'),
        $error = $('#vigenereerror');
    $encrypt.click(function () {
        if (!allLetters($keyword.val()))
            $error.text("The keyword must not contain numbers or special symbols/characters.");
        else {
            $.ajax({
                url: "/workbench/vigeneretool.json",
                type: 'GET',
                dataType: 'text',
                data: {
                    plaintext: $plaintext.val(),
                    keyword: $keyword.val()
                }
            }).done(function (r) {
                $ciphertext.val(r)
            }).fail(function (e) {
                console.log(e);
            });
        }
    });
    $decrypt.click(function () {
        if (!allLetters($keyword.val()))
            $error.text("The keyword must not contain numbers or special symbols/characters.");
        else {
            $.ajax({
                url: "/workbench/vigeneretool/decrypt.json",
                type: 'GET',
                dataType: 'text',
                data: {
                    ciphertext: $ciphertext.val(),
                    keyword: $keyword.val()
                }
            }).done(function (r) {
                $plaintext.val(r)
            }).fail(function (e) {
                console.log(e);
            });
        }
    });
    function allLetters(input) {
        var result = true;
        for (var i = 0; i < input.length; i++) {
            if (input.charAt(i).toUpperCase() == input.charAt(i).toLowerCase())
                result = false;
        }
        return result;
    }
})
