var myLayout;
function doOnLoad() {
    myLayout = new dhtmlXLayoutObject({
        parent: "layoutObj",
        pattern: "3L"
    });
    // access through cells
    myLayout.cells("a").setText("Hints");
    myLayout.cells("b").setText("Challenge");
    myLayout.cells("c").setText("Workbench");
   // dhxComponent.cells("b").attachObject("challenge");
    myLayout.cells("b").attachHTMLString('<div class="demo"> <div id="textblock"> <div id="scramble"></div> <span id="charsCustom"></span> <span id="charsNumbers"></span><br> <span id="charsUppercase"></span> <span id="charsLowercase"></span> <div id="newClass"></div> </div> </div>');
    // access through items deprecated from 4.0

    //ScrambleText
    var tl = new TimelineMax({onUpdate:updateSlider});

    tl.to("#scramble", 3, {scrambleText:{text:"ScrambleText allows you to animate the scrambling of text.", chars:"lowerCase", revealDelay:0.5, tweenLength:false, ease:Linear.easeNone}})

        .to("#charsCustom", 4, {scrambleText:{text:"Specify a set of characters to scramble like 'XO'", chars:"XO", revealDelay:1, tweenLength:false, speed:0.4, ease:Linear.easeNone}})


        .to("#charsNumbers", 4, {scrambleText:{text:"or use only numbers,", chars:"0123456789",  ease:Linear.easeNone}})

        .to("#charsUppercase", 2, {scrambleText:{text:"UPPERCASE", chars:"upperCase",  speed:0.3, ease:Linear.easeNone}})

        .to("#charsLowercase", 2, {scrambleText:{text:"or lowercase.", chars:"lowerCase", speed:0.3,  ease:Linear.easeNone}})

        .to("#newClass", 2, {scrambleText:{text:"Even apply a custom class to the text.", chars:"lowerCase", speed:0.3, newClass:"orange", ease:Linear.easeNone, revealDelay:0.5, tweenLength:false}})


    $("#scrambleSlider").slider({
        range: false,
        min: 0,
        max: 1,
        step:.001,
        slide: function ( event, ui ) {
            tl.progress( ui.value ).pause();
        },
        stop: function () {
            tl.play();
        }
    });

    function updateSlider() {
        $("#scrambleSlider").slider("value", tl.progress());
    }
}