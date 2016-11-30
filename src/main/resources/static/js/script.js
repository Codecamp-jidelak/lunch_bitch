$(document).ready(function() {
    $('.restBlock').click(function () {
        var checkboxObject = $(this).find('input[type=checkbox]');
        checkboxObject.prop("checked", !checkboxObject.prop("checked"));
        checkboxObject.parent().parent().toggleClass('highlightRestaurant');
    });
});

$(document).ready(function() {
    $('input:radio[name=type]').change(function() {
        var placeholder = "";

        if (this.value == 'address') {
            placeholder = "adresa, město";
        }
        else if (this.value == 'keyword') {
            placeholder = "klíčové slovo";
        }
        document.getElementById("keyword").setAttribute("placeholder", placeholder);
    });
});

document.documentElement.className = 'js';

$(document).ready(function(){
    $(".showForm").click(function(){
        if($("#searchForm").is(":visible")){
            $("#searchForm").hide();
        } else {
            $("#searchForm").show();
        }
        return false;
    });
});


