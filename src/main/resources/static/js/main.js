$(function (){
    portfolioRandomData();
})

function portfolioRandomData() {
    $.ajax({
        url: '/api/user/portfolio',
        method: 'GET',
        dataType: 'JSON',
        success: function (data) {
            let portfolioHtml = ``;
            $.each(data, function (idx, ele) {
                portfolioHtml += `<div>${ele.name}</div>`;
            })
            $('#second-div').html(portfolioHtml);
        }, error: function (xhr, textStatus, errorThrown) {
            alert("error: " + textStatus);
            console.log(xhr.responseText);
        }
    })
}