
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%@attribute name="title" required="true"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <title>${title}</title>
             <!-- Custom styles for this template -->
         <link href="css/starter-template.css" rel="stylesheet">
         <link href="css/styles.css" rel="stylesheet">
          <link href="css/dashboard.css" rel="stylesheet">
          <link href="css/jquery-ui.css" rel="stylesheet">
    </head>
    <body>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery-ui.min.js"></script> 
        
<script type="text/javascript">
$(document).ready(function() {
 
//Код первой вкладки (стандартное использование)
   $("#datepicker_dBegin").datepicker({dateFormat:"dd-mm-yy"});;
    $("#datepicker_dEnd").datepicker({dateFormat:"dd-mm-yy"});;
});
</script>
        <section class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">         
            <jsp:doBody/>        
        </section>
    </body>
</html>
