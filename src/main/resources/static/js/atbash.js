/**
 * Created by Duke on 1/19/17.
 */
'use strict';
$(document).ready(function () {
    var $plaintext = $('#plaintextBox'),
        $ciphertext = $('#ciphertextBox'),
        $encrypt = $('#encrypt'),
        $decrypt = $('#decrypt');
    $encrypt.click(function () {
        $.ajax({url: "/workbench/atbashtool.json",
            type: 'GET',
            dataType: 'text',
            data: {input: $plaintext.val(),
            punctuation: true}
        }).done(function (r) {
            $ciphertext.val(r)
        }).fail(function (e) {
            console.log(e)
        })
    });
    $decrypt.click(function () {
        $.ajax({url: "/workbench/atbashtool.json",
            type: 'GET',
            dataType: 'text',
            data: {input: $ciphertext.val(),
                punctuation: true}
        }).done(function (r) {
            $plaintext.val(r)
        }).fail(function (e) {
            console.log(e)
        })
    });
})