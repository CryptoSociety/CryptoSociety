/**
 * Created by Duke on 1/19/17.
 */
'use strict';

$(document).ready(function() {
    var $plaintext = $('#plaintextBox'),
        $ciphertext = $('#ciphertextBox'),
        $encrypt = $('#encrypt'),
        $decrypt = $('#decrypt'),
        $shift = $('#shiftAmount');
    $encrypt.click(function () {
        if(isNaN($shift.val()))
            $('#error').text("The shift must be a positive whole number.");
        else {
            $('#error').text("");
            $.ajax({
                url: "/workbench/caesartool.json",
                type: 'GET',
                dataType: 'text',
                data: {
                    plaintext: $plaintext.val(),
                    shift: $shift.val(),
                    punctuation: true
                }
            }).done(function (r) {
                $ciphertext.val(r)
            }).fail(function (e) {
                console.log(e);
            });
        }
    });
    $decrypt.click(function () {
        $.ajax({url: "/workbench/caesartool/decrypt.json",
            type: 'GET',
            dataType: 'text',
            data: {ciphertext: $ciphertext.val(),
                shift: $shift.val(),
                punctuation: true}
        }).done(function (r) {
            $plaintext.val(r)
        }).fail(function (e) {
            console.log(e);
        });
    });
});
