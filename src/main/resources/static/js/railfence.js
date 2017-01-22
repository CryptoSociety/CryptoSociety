/**
 * Created by Duke on 1/20/17.
 */
'use strict';

$(document).ready(function() {
    var $plaintext = $('#plaintextBox'),
        $ciphertext = $('#ciphertextBox'),
        $encrypt = $('#encrypt'),
        $decrypt = $('#decrypt'),
        $rails = $('#numberOfRails'),
        $error = $('#error');
    $encrypt.click(function () {
        if (isNaN($rails.val()))
            $error.text("The number of rails must be a positive whole number.");
        else {
            $error.text("");
            $.ajax({
                url: "/workbench/railfencetool.json",
                type: 'GET',
                dataType: 'text',
                data: {
                    plaintext: $plaintext.val(),
                    rails: $rails.val()
                }
            }).done(function (r) {
                $ciphertext.val(r)
            }).fail(function (e) {
                console.log(e);
            });
        }
    });
    $decrypt.click(function () {
        if (isNaN($rails.val()))
            $error.text("The number of rails must be a positive whole number.");
        else {
            $error.text("");
            $.ajax({
                url: "/workbench/railfencetool/decrypt.json",
                type: 'GET',
                dataType: 'text',
                data: {
                    ciphertext: $ciphertext.val(),
                    rails: $rails.val()
                }
            }).done(function (r) {
                $plaintext.val(r)
            }).fail(function (e) {
                console.log(e);
            });
        }
    });
})