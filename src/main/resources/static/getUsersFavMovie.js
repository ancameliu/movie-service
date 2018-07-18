function search() {
    $.getJSON(
        "http://localhost:8080/api/v1/users/favoritemovie/" + $("#search").val(),

        function(data) {
            table = $("<table><th>id</th><th>name</th>")
                    .attr("id", "result");

            data.forEach(function(user) {

                row = $("<tr></tr>")
                .append($("<td></td>").text(user.id))
                .append($("<td></td>").text(user.name));

                table.append(row);
            })
            $("#result").replaceWith(table);
        }
    )
};