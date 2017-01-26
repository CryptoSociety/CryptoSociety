"use strict";

$(document).ready(function () {
    var myLayout;
    var pattern = "3L";
    var formContent = document.getElementById("formContent");
    var challengeContent = document.getElementById("challenge");
    var currentPattern;

    function loadLayout() {
        var width = window.innerWidth;

        if (width < 600) {
            pattern = "3E"
        } else {
            pattern = "3L"
        }

        if (pattern == currentPattern) {
            return
        } else {
            currentPattern = pattern
        }

        if (myLayout) {
            myLayout.unload()
        }

        myLayout = new dhtmlXLayoutObject({
            parent: document.getElementById("layoutObj"),  // parent container
            pattern: pattern         // layout's pattern,

        });

        myLayout.cells("a").setWidth(300);
        // myLayout.cells("c").attachHTMLString('<form><textarea cols="100" rows="17">Fer</textarea></form>');
        myLayout.cells("c").attachObject(formContent);
        myLayout.cells("b").attachObject(challengeContent);
        myLayout.cells("a").setText("Hints");
        myLayout.cells("b").setText("Challenge");
        myLayout.cells("c").setText("Workbench");

    }

    window.onresize = loadLayout();
    loadLayout();
});

//myLayout.cells("b").attachHTMLString('<div class="demo"> <div id="textblock"> <div id="scramble"></div> <span id="charsCustom"></span> <span id="charsNumbers"></span><br> <span id="charsUppercase"></span> <span id="charsLowercase"></span> <div id="newClass"></div> </div> </div>');


// access through items deprecated from 4.0


//  Modal--------------------------------------------
// $('.button').click(function(){
//     var buttonId = $(this).attr('id');
//     $('#modal-container').removeAttr('class').addClass(buttonId);
//     $('body').addClass('modal-active');
// })
//
// $('#modal-container').click(function(){
//     $(this).addClass('out');
//     $('body').removeClass('modal-active');
// });


