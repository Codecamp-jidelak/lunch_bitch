function onBlur(el) {
    if (el.value == '') {
        el.value = el.defaultValue;
    }
}
function onFocus(el) {
    if (el.value == el.defaultValue) {
        el.value = '';
    }
}

$(document).ready(function() {
    $('.restBlock').click(function ()
    {
        var checkboxObject = $(this).find('input[type=checkbox]');
        checkboxObject.prop("checked", !checkboxObject.prop("checked"));
        checkboxObject.parent().parent().toggleClass('highlightRestaurant');
    });
});
