var weekday = new Array(7);
weekday[0] = "SUNDAY";
weekday[1] = "MONDAY";
weekday[2] = "TUESDAY";
weekday[3] = "WEDNESDAY";
weekday[4] = "THURSDAY";
weekday[5] = "FRIDAY";
weekday[6] = "SATURDAY";

function getDayName(dateString) {
    var d = new Date(dateString);
    var n = weekday[d.getDay()];
    return n.substring(0, 3);
}
