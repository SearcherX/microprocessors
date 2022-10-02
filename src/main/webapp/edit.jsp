<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit</title>
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/style.css" rel="stylesheet"/>
</head>
<body>
<main>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-6 text-center">
                <c:if test="${action.equals('update')}">
                    <h3>Редактировать информацию</h3>
                </c:if>
                <c:if test="${action.equals('create')}">
                    <h3>Добавить информацию</h3>
                </c:if>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-3 bg-light">
                <c:if test="${action.equals('update')}">
                <form action="UpdateServlet" method="post">
                    </c:if>
                    <c:if test="${action.equals('create')}">
                    <form action="CreateServlet" method="post">
                        </c:if>
                        <div class="row">
                            <div class="col-12">
                                <label for="model" class="form-label">Модель микропроцессора</label>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="model" name="model"
                                           placeholder="Название" required="" value="${microprocessor.model}">
                                </div>
                            </div>

                            <div class="col-12">
                                <label for="dataBitDepth" class="form-label">Разрядность</label>
                                <div class="input-group">
                                    <span class="input-group-text">данных: </span>
                                    <input type="text" class="form-control" id="dataBitDepth" name="dataBitDepth"
                                           placeholder="бит" required="" value="${microprocessor.dataBitDepth}">
                                    <span class="input-group-text">адреса: </span>
                                    <input type="text" class="form-control" id="addressBitDepth"
                                           name="addressBitDepth"
                                           placeholder="бит" required="" value="${microprocessor.addressBitDepth}">
                                </div>
                            </div>

                            <div class="col-12">
                                <label for="clockSpeeds" class="form-label">Тактовая частота</label>
                                <input type="text" class="form-control" id="clockSpeeds" name="clockSpeeds"
                                       placeholder="МГц" required=""
                                       value='${microprocessor.getClockSpeedsStr()}'>
                            </div>

                            <div class="col-12">
                                <label for="addressSpaces" class="form-label">Адрессное пространство</label>
                                <input type="text" class="form-control" id="addressSpaces" name="addressSpaces"
                                       placeholder="байт" required=""
                                       value="${microprocessor.addressSpaces}">
                            </div>

                            <div class="col-12">
                                <label for="numberOfCommands" class="form-label">Число команд</label>
                                <input type="text" class="form-control" id="numberOfCommands"
                                       name="numberOfCommands"
                                       placeholder="количество" value="${microprocessor.numberOfCommands}">
                            </div>

                            <div class="col-12">
                                <label for="numberOfElements" class="form-label">Число элементов</label>
                                <input type="text" class="form-control" id="numberOfElements"
                                       name="numberOfElements"
                                       placeholder="количество"
                                       required="" value="${microprocessor.numberOfElements}">
                            </div>

                            <div class="col-12">
                                <label for="releaseYear" class="form-label">Год выпуска</label>
                                <input type="text" class="form-control" id="releaseYear" dirname="releaseYear"
                                       placeholder="год" required=""
                                       name="releaseYear" value="${microprocessor.releaseYear}">
                            </div>

                        </div>
                        <input type="hidden" name="id" id="id" value="${microprocessor.id}">
                        <div class="modal-footer mt-3">
                            <button type="button" class="btn btn-secondary"
                                    onclick="window.location='${pageContext.request.contextPath}/index';">Отмена
                            </button>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </div>
                    </form>
            </div>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
