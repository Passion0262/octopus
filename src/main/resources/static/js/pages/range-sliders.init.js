$(document).ready(function(){
    $("#score").ionRangeSlider({skin:"round",min:0,max:1e2}),
        $("#discount").ionRangeSlider({skin:"round", min:0, max:1, step:0.001}),
        $("#range_02").ionRangeSlider({skin:"round",min:100,max:1e3,from:550}),
        $("#range_03").ionRangeSlider({skin:"round",type:"double",grid:!0,min:0,max:1e3,from:200,to:800,prefix:"$"}),
        $("#range_04").ionRangeSlider({skin:"round",type:"double",grid:!0,min:-1e3,max:1e3,from:-500,to:500}),
        $("#range_05").ionRangeSlider({skin:"round",type:"double",grid:!0,min:-1e3,max:1e3,from:-500,to:500,step:250}),
        $("#range_06").ionRangeSlider({skin:"round",grid:!0,from:3,values:["January","February","March","April","May","June","July","August","September","October","November","December"]}),
        $("#range_07").ionRangeSlider({skin:"round",grid:!0,min:1e3,max:1e6,from:2e5,step:1e3,prettify_enabled:!0}),
        $("#range_08").ionRangeSlider({skin:"round",min:100,max:1e3,from:550,disable:!0}),
        $("#range_09").ionRangeSlider({skin:"round",grid:!0,min:18,max:70,from:30,prefix:"Age ",max_postfix:"+"}),
        $("#range_10").ionRangeSlider({skin:"round",type:"double",min:100,max:200,from:145,to:155,prefix:"Weight: ",postfix:" million pounds",decorate_both:!0}),
        $("#range_11").ionRangeSlider({skin:"round",type:"single",grid:!0,min:-90,max:90,from:0,postfix:"Ã‚Â°"}),
        $("#range_12").ionRangeSlider({skin:"round",type:"double",min:1e3,max:2e3,from:1200,to:1800,hide_min_max:!0,hide_from_to:!0,grid:!0})
});