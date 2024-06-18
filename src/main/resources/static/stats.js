google.charts.load("current", {packages: ["corechart"]});
google.charts.setOnLoadCallback(drawStatus);

// function drawMileage() {
//     let res = [['Транспорт', 'Пробег']];
//
//     for (let i = 0; i < mileageString.length; i++) {
//         res.push([mileageString[i], mileageInt[i]]);
//     }
//
//     var data = google.visualization.arrayToDataTable(res);
//
//     let options = {
//         title: 'По пробегу',
//         hAxis: {title: 'Транспорт'},
//         vAxis: {title: 'Пробег'},
//         bar: {groupWidth: "80%"},
//         legend: {position: "none"}
//     };
//
//     let chart = new google.visualization.ColumnChart(document.getElementById('drawMileage'));
//     chart.draw(data, options);
// }

function drawStatus() {
    let res = [['Статус', 'Количество']];

    for (let i = 0; i < statusString.length; i++) {
        res.push([statusString[i], statusInt[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    var options = {
        title: '',
        pieHole: 0.2,
    };

    var chart = new google.visualization.PieChart(document.getElementById('drawStatus'));
    chart.draw(data, options);
}