"use strict";

$(document).ready(function () {
    var myLayout;
    var pattern = "3L";
    var formContent = document.getElementById("formContent");
    var challengeContent = document.getElementById("challenge");
    var scratchpad = document.getElementById("scratchpad");
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

        myLayout.cells("a").setWidth(400);
        myLayout.cells("a").attachObject(scratchpad);
        myLayout.cells("c").attachObject(formContent);
        myLayout.cells("b").attachObject(challengeContent);
        myLayout.cells("a").setText("Scratchpad");
        myLayout.cells("b").setText("Challenge");
        myLayout.cells("c").setText("Workbench");

    }

    window.onresize = loadLayout();
    loadLayout();
});

