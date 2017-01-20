/**
 * Created by Duke on 1/19/17.
 */
'use strict';
var $plaintext = $('#plaintextBox'),
    $ciphertext = $('#ciphertextBox'),
    $encrypt = $('#encrypt'),
    $decrypt = $('#decrypt'),
    $key = $('[name="key[]"').map(function(){return this.value}).toArray(),
    $list = $('#lettersList');
var $array = $("[name='key[]']");
$('#keyFields').find('input').on("keyup", function () {
    for(var i=0;i<$array.length;i++) {
    var value =  $array.eq(i).val();
    
}
});
$encrypt.click(function () {
    $.ajax({url: "/workbench/kamasutratool.json",
        type: 'GET',
        dataType: 'text',
        data: {input: $plaintext.val(),
            key: $key.join(""),
            punctuation: true}
    }).done(function (r) {
        $ciphertext.val(r)
    }).fail(function (e) {
        console.log(e)
    })
});
$decrypt.click(function () {
    $.ajax({url: "/workbench/kamasutratool.json",
        type: 'GET',
        dataType: 'text',
        data: {input: $ciphertext.val(),
            key: $key.join(""),
            punctuation: true}
    }).done(function (r) {
        $plaintext.val(r)
    }).fail(function (e) {
        console.log(e)
    })
});
