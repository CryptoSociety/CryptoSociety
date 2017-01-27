console.log("Test");
// $(document).ready(function () {
$("#scheme").change(function () {
    var val = $("#scheme").val();
    if (val == "caesar") {
        $("#key").html("<label for='key'>Shift (Positive integer)</label><input class='rails' name='cryptokey' th:value='' th:field='*{cryptokey}'/>");
    } else if (val == "atbash") {
        $("#key").html("<label for='key'>Atbash doesn't use a key.</label><input name='cryptokey' hidden th:value='atbash' th:field='*{cryptokey}'/>");

    } else if (val == "kamasutra") {
        $("#key").html("<label for='key'>Key</label>" +
            "<ul id='kamalist' style='list-style:none'>" +
            "<li><input id='in1' class='ksin' maxlength='1'/><span> = </span><input id='in2' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in3' class='ksin' maxlength='1'/><span> = </span><input id='in4' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in5' class='ksin' maxlength='1'/><span> = </span><input id='in6' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in7' class='ksin' maxlength='1'/><span> = </span><input id='in8' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in9' class='ksin' maxlength='1'/><span> = </span><input id='in10' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in11' class='ksin' maxlength='1'/><span> = </span><input id='in12' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in13' class='ksin' maxlength='1'/><span> = </span><input id='in14' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in15' class='ksin' maxlength='1'/><span> = </span><input id='in16' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in17' class='ksin' maxlength='1'/><span> = </span><input id='in18' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in19' class='ksin' maxlength='1'/><span> = </span><input id='in20' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in21' class='ksin' maxlength='1'/><span> = </span><input id='in22' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in23' class='ksin' maxlength='1'/><span> = </span><input id='in24' class='ksin' maxlength='1'/></li>" +
            "<li><input id='in25' class='ksin' maxlength='1'/><span> = </span><input id='in26' class='ksin' maxlength='1'/></li>" +
            "</ul>" +
            "<input name='cryptokey' id='finalKey' hidden  th:value='' th:field='*{cryptokey}'/>");
        $(".ksin").change(kamasutraInputManipulation);
        function kamasutraInputManipulation() {
            var final = $("#finalKey");
            var one = $("#in1");
            var two = $("#in2");
            var three = $("#in3");
            var four = $("#in4");
            var five = $("#in5");
            var six = $("#in6");
            var seven = $("#in7");
            var eight = $("#in8");
            var nine = $("#in9");
            var ten = $("#in10");
            var eleven = $("#in11");
            var twelve = $("#in12");
            var thirteen = $("#in13");
            var fourteen = $("#in14");
            var fifteen = $("#in15");
            var sixteen = $("#in16");
            var seventeen = $("#in17");
            var eighteen = $("#in18");
            var nineteen = $("#in19");
            var twenty = $("#in20");
            var twentyone = $("#in21");
            var twentytwo = $("#in22");
            var twentythree = $("#in23");
            var twentyfour = $("#in24");
            var twentyfive = $("#in25");
            var twentysix = $("#in26");
            final.val(one.val() + two.val() + three.val() + four.val() + five.val() + six.val() + seven.val() + eight.val() + nine.val() + ten.val() + eleven.val() + twelve.val() + thirteen.val() + fourteen.val() + fifteen.val() + sixteen.val() + seventeen.val() + eighteen.val() + nineteen.val() + twenty.val() + twentyone.val() + twentytwo.val() + twentythree.val() + twentyfour.val() + twentyfive.val() + twentysix.val());
        }
    } else if (val == "railfence") {
        $("#key").html("<label for='key'>Number of Rails (Positive integer)</label><input class='rails' name='cryptokey' th:value='' th:field='*{cryptokey}'/>");
    } else if (val == "vigenere") {
        $("#key").html("<label for='key'>Keyword</label><input class='rails' name='cryptokey' th:value='' th:field='*{cryptokey}'/>");
    }
});

// });