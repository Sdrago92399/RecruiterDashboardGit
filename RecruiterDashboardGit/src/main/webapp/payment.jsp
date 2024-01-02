<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<button id="rzp-button1">Pay with Razorpay</button>
	<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
	<script>
	var options = {
	    "key": Globals., // Enter the Key ID generated from the Dashboard
	    "amount": ${amt}, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
	    "currency": "INR",
	    "name": "Acme Corp",
	    "description": "Test Transaction",
	    "image": "https://example.com/your_logo",
	    "order_id": "order_IluGWxBm9U8zJ8", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
	    "callback_url": "https://eneqd3r9zrjok.x.pipedream.net/",
	    "prefill": {
	        "name": "Gaurav Kumar",
	        "email": "gaurav.kumar@example.com",
	        "contact": "9000090000"
	    },
	    "notes": {
	        "address": "Razorpay Corporate Office"
	    },
	    "theme": {
	        "color": "#3399cc"
	    }
	};
	var rzp1 = new Razorpay(options);
	document.getElementById('rzp-button1').onclick = function(e){
	    rzp1.open();
	    e.preventDefault();
	}
	</script>
</body>
</html>