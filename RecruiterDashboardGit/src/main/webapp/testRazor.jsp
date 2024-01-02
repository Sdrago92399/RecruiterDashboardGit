<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Razorpay Java Integration</title>
 <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.2/css/all.min.css">

    <style>
         *{
            box-sizing: border-box;
            font-family: 'Noto Sans', sans-serif;
        }
        body{
            background-color: #02042b;   
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0px;
            min-height: 100vh;
        }
        .container{
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
            overflow: hidden;
            width: 60vw;
            max-width: 100%;
            height: 60vh;
            /* height: 700px; */
            margin: 10px;
        }
        img{
          width: 120px;  
          height: 120px;
          margin-top: 5px;
        }
        .button a{
            text-decoration: none;
            display: block;
            text-align: center;
            background-color:#2b84ea;
            border: 2px solid #2b84ea;
            border-radius: 50px;
            color: white;
            padding: 15px;
            font-family: inherit;
            font-size: 16px;
            width: 200px;
        }
        .button a:hover{
           color:white;
           background:#2ecc71;
           border:2px solid #2ecc71;
           cursor:pointer;
        } 
    </style>
</head>
<body>
 <div class="container">
        <h1>${message}</h1>
        <div class="button"><a href="index.jsp" class="button"> ${buttontext} </a></div>        
    </div>

</body>
</html>