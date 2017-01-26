/**
 * Created by Duke on 1/19/17.
 */
'use strict';
var $plaintext = $('#kamasutraplaintextBox'),
    $ciphertext = $('#kamasutraciphertextBox'),
    $encrypt = $('#kamasutraencrypt'),
    $decrypt = $('#kamasutradecrypt'),
    $list = $('#lettersList'),
    $array = $("[name='key[]']"),
    $error = $('#kamasutraerror');
$('#keyFields').find('input').on("keyup", function () {
    var alphabet = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
    for (var i = 0; i < $array.length; i++) {
        var value = $array.eq(i).val();
        alphabet = alphabet.replace(value.toUpperCase(), "");
    }
    $list.html(alphabet);
});
$encrypt.click(function () {
    if ($list.html().toString().trim() != "")
        $error.text("You need to use all letters of the alphabet");
    else {
        $error.text("");
        $.ajax({
            url: "/workbench/kamasutratool.json",
            type: 'GET',
            dataType: 'text',
            data: {
                input: $plaintext.val(),
                key: $('[name="key[]"').map(function () {
                    return this.value
                }).toArray().join(""),
                punctuation: true
            }
        }).done(function (r) {
            $ciphertext.val(r)
        }).fail(function (e) {
            console.log(e)
        })
    }
});
$decrypt.click(function () {
    if ($list.html().toString().trim() != "")
        $error.text("You need to use all letters of the alphabet");
    else {
        $error.text("");
        $.ajax({
            url: "/workbench/kamasutratool.json",
            type: 'GET',
            dataType: 'text',
            data: {
                input: $ciphertext.val(),
                key: $('[name="key[]"').map(function () {
                    return this.value
                }).toArray().join(""),
                punctuation: true
            }
        }).done(function (r) {
            $plaintext.val(r)
        }).fail(function (e) {
            console.log(e)
        })
    }
});
