<jsp:useBean id="obj" class="conversie.ConversieBean" scope="application"/>
<jsp:setProperty name="obj" property="*"/>
<html>
<body>
   Conversie:
   <p>
<%=obj.getOutput(obj.getSuma(),obj.getValuta()) %>
</body>
</html>
