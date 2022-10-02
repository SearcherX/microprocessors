<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Микропроцессоры</title>
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/style.css" rel="stylesheet"/>
</head>
<body>
<main>
    <div class="container">
        <h2 class="text-center">MySQL CRUD</h2>
        <div class="row">
            <div class="col-12 mb-3 mt-3">
                <a href="CreateServlet" class="btn btn-success">Добавить</a>
            </div>
            <div class="col-12">
                <table class="bg-light">
                    <thead>
                    <tr>
                        <th rowspan="2">Модель МП</th>
                        <th colspan="2">Разрядность, бит</th>
                        <th rowspan="2">Тактовая частота, МГц</th>
                        <th rowspan="2">Адрессное пространство, байт</th>
                        <th rowspan="2">Число команд</th>
                        <th rowspan="2">Число элементов</th>
                        <th rowspan="2">Год выпуска</th>
                        <th rowspan="2" colspan="2">Действия</th>
                    </tr>
                    <tr>
                        <th>данных</th>
                        <th>адреса</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="record" items="${microprocessors}" varStatus="loop1">
                        <tr>
                            <td><c:out value="${record.model}"/></td>
                            <td><c:out value="${record.dataBitDepth}"/></td>
                            <td><c:out value="${record.addressBitDepth}"/></td>
                            <td><c:out value="${record.getClockSpeedsStr()}"/></td>
                            <td><c:out value="${record.addressSpaces}"/></td>
                            <td><c:out value="${record.numberOfCommands}"/></td>
                            <td><c:out value="${record.numberOfElements}"/></td>
                            <td><c:out value="${record.releaseYear}"/></td>
                            <td>
                                <a href="UpdateServlet?id=${record.id}&action=update" class="btn btn-warning">Редактировать</a>
                            </td>
                            <td>
                                <form method="post" action='DeleteServlet'>
                                    <input type="hidden" name="id" value="${record.id}">
                                    <input type="submit" value="Удалить" class="btn btn-danger">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp" />

<script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>