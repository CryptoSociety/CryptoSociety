/**
 * Created by Duke on 1/20/17.
 */
'use strict';
$(document).ready(function() {
    var $plaintext = $('#plaintextBox'),
        $ciphertext = $('#ciphertextBox'),
        $encrypt = $('#encrypt'),
        $decrypt = $('#decrypt'),
        $keyword = $('#keyword');
    $encrypt.click(function () {
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
    });
    $decrypt.click(function () {
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
    });
})
