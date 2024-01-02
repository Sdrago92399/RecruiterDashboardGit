<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Job Dashboard | By Code Info</title>
<link rel="stylesheet" href="style1.css" />
<!-- Font Awesome Cdn Link -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
<style>
* {
	margin: 0;
	padding: 0;
	outline: none;
	border: none;
	text-decoration: none;
	list-style: none;
	box-sizing: border-box;
	font-family: "Poppins", sans-serif;
}

body {
	background: rgb(233, 233, 233);
}

.container {
	display: flex;
	width: 1400px;
	margin: auto;
}

nav {
	position: fixed;
	top: 0;
	left: 0;
	bottom: 0;
	width: 280px;
	height: 110vh;
	background: #fff;
	display: block;
}

.navbar {
	width: 80%;
	margin: 0 auto;
}

.logo {
	margin: 2rem 0 1rem 0;
	padding-bottom: 3rem;
	display: flex;
	align-items: center;
}

.logo img {
	width: 50px;
	height: 50px;
	border-radius: 50%;
}

.logo h1 {
	margin-left: 1rem;
	text-transform: uppercase;
}

ul {
	margin: 0 auto;
}

li {
	padding-bottom: 2rem;
}

li a {
	font-size: 16px;
	color: rgb(85, 83, 83);
}

.nav-item {
	
}

nav i {
	width: 50px;
	font-size: 18px;
	text-align: center;
}

.logout {
	position: absolute;
	bottom: 20px;
}

/* Main Section */
.main {
	width: 100%;
}

.main-top {
	width: 100%;
	background: #fff;
	padding: 10px;
	text-align: center;
	font-size: 18px;
	letter-spacing: 2px;
	text-transform: uppercase;
	color: rgb(43, 43, 43);
}

.main-body {
	padding: 10px 10px 10px 20px;
}

h1 {
	margin: 20px 10px;
}

.search_bar {
	display: flex;
	padding: 10px;
	justify-content: space-between;
}

.search_bar input {
	width: 50%;
	padding: 10px;
	border: 1px solid rgb(190, 190, 190);
}

.search_bar input:focus {
	border: 1px solid blueviolet;
}

.search_bar select {
	border: 1px solid rgb(190, 190, 190);
	padding: 10px;
	margin-left: 2rem;
}

.search_bar .filter {
	width: 300px;
}

.tags_bar {
	width: 55%;
	display: flex;
	padding: 10px;
	justify-content: space-between;
}

.tag {
	background: #fff;
	padding: 10px 15px;
	border-radius: 20px;
	display: flex;
	align-items: center;
	font-size: 13px;
	cursor: pointer;
}

.tag i {
	margin-right: 0.7rem;
}

.row {
	display: flex;
	padding: 10px;
	margin-top: 1.3rem;
	justify-content: space-between;
}

.row p {
	color: rgb(54, 54, 54);
	font-size: 15px;
}

.row p span {
	color: blueviolet;
}

.job_card {
	width: 100%;
	padding: 15px;
	cursor: pointer;
	display: flex;
	border-radius: 10px;
	background: #fff;
	margin-bottom: 15px;
	justify-content: space-between;
	border: 2px solid rgb(190, 190, 190);
	box-shadow: 0 20px 30px rgba(0, 0, 0, 0.1);
}

.job_details {
	display: flex;
}

.job_details .img {
	display: flex;
	justify-content: center;
	align-items: center;
}

.job_details .img i {
	width: 70px;
	font-size: 3rem;
	margin-left: 1rem;
	padding: 10px;
	color: rgb(82, 22, 138);
	background: rgb(216, 205, 226);
}

.job_details .text {
	margin-left: 2.3rem;
}

.job_details .text span {
	color: rgb(116, 112, 112);
}

.job_salary {
	text-align: right;
	color: rgb(54, 54, 54);
}

.job_card:active {
	border: 2px solid blueviolet;
	transition: 0.4s;
}

#profile {
	display: none;
}

#profile body {
	background-color: #f3f3f3;
	font-family: Arial, sans-serif;
}

#profile .col-md-10.center {
	margin: 0 auto;
	margin-top: 30px;
}

.
#profile panel-warning {
	border-color: #d9534f;
}

#profile .content-box-header {
	background-color: #d9534f;
	color: white;
	padding: 10px;
	border-bottom: 1px solid #ddd;
}

#profile .form-group {
	margin-bottom: 20px;
}

#profile label {
	display: block;
	margin-bottom: 5px;
	color: #333;
}

#profile .form-control {
	width: 100%;
	padding: 10px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 16px;
}

#profile .disable {
	background-color: #eee;
	cursor: not-allowed;
}

#profile .rform {
	background-color: #fff;
	padding: 2%;
	border: 1px solid #ddd;
	border-radius: 4px;
}

#profile .btn-primary {
	background-color: #428bca;
	color: #fff;
	border: none;
	padding: 10px 20px;
	font-size: 16px;
	cursor: pointer;
	border-radius: 4px;
}

#profile .btn-primary:hover {
	background-color: #3071a9;
}

#trnsc {
	display: none;
}

#trnsc body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 0;
}

#trnsc header {
	background-color: #d9534f;
	color: #fff;
	padding: 1em;
	text-align: center;
}

#trnsc section {
	max-width: 1300px;
	margin: 20px auto;
	background-color: #fff;
	padding: 20px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

#trnsc table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

#trnsc th, td {
	padding: 12px;
	text-align: left;
	border-bottom: 1px solid #ddd;
}

#trnsc th {
	background-color: #333;
	color: #fff;
}

#trnsc tr:hover {
	background-color: #f5f5f5;
}

#trnsc .deduction {
	color: #FF0000; /* Red color for deduction */
}

#trnsc .addition {
	color: #008000; /* Green color for addition */
}

#trnsc .btn-primary {
	background-color: #428bca;
	color: #fff;
	border: none;
	padding: 10px 20px;
	font-size: 16px;
	cursor: pointer;
	border-radius: 4px;
	left: 50%;
	-ms-transform: translateX(50%);
	transform: translateX(50%);
}

#trnsc .btn-primary:hover {
	background-color: #3071a9;
}

#recharge {
	display: none;
}

#passReset {
	display: none;
}

.content {
	margin-left: 25%;
	padding: 1px 16px;
}

.pagination {
  position: relative;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(13px);
  border-radius: 2px;
}
.pagination li {
  list-style-type: none;
  display: inline-block;
}
.pagination li button {
  position: relative;
  padding: 20px 25px;
  text-decoration: none;
  color: black;
  font-weight: 500;
}
.pagination li button:hover,
.pagination li button.active {
  background: rgba(126, 25, 255, 0.2);
}

</style>



</head>
<body>
	<div class="container">
		<nav>
			<div class="navbar">
				<div class="logo">
					<img src="/pic/logo.jpg" alt="">
					<h1>Eduinq</h1>
				</div>
				<ul>
					<li><a href="#" onclick="showPage('profile')"> <i
							class="fas fa-user"></i> <span class="nav-item">My Profile</span>
					</a></li>

					<li><a href="#"> <i class="fas fa-chart-bar"></i> <span
							class="nav-item">Access Database</span>
					</a></li>

					<li><a href="#"> <i class="fas fa-tasks"></i> <span
							class="nav-item">Saved Resume</span>
					</a></li>

					<li><a href="#" onclick="showPage('trnsc')"> <i
							class="fab fa-dochub"></i> <span class="nav-item">Wallet
								History</span>
					</a></li>

					<li><a href="#" onclick="showPage('recharge')"> <i class="fas fa-cog"></i> <span
							class="nav-item">Wallet Recharge</span>
					</a></li>

					<li><a href="#"> <i class="fas fa-question-circle"></i> <span
							class="nav-item">Chat With Team</span>
					</a></li>

					<li><a href="#" onclick="showPage('passReset')"> <i class="fas fa-question-circle"></i> <span
							class="nav-item">Password Change</span>
					</a></li>

					<li><a href="#" class="logout"> <i
							class="fas fa-sign-out-alt"></i> <span class="nav-item">Logout</span>
					</a></li>
				</ul>
			</div>
		</nav>
	</div>
	<div id="profile" class="content">
		<div class="col-md-10 center">
			<div class="row" id="MyProfile">
				<div class="col-md-12 panel-warning">
					<div class="content-box-header panel-heading">
						<div class="panel-title">My Profile</div>
						<div class="panel-options">
							<a href="#" data-rel="collapse"><i
								class="glyphicon glyphicon-calendar"></i></a> <a href="#"
								data-rel="reload"><i class="glyphicon glyphicon-cog"></i></a>
						</div>
					</div>
					<div class="container" style="margin-top: 30px; padding: 2%;">
						<div class="row">
							<div class="col-md-6 rform">
								<form action="#" method="post">
									<div class="form-group">
										<label for="company">Name</label> <input type="text"
											class="form-control disable" id="company"
											placeholder="${user}" name="name" readonly="readonly">
									</div>
									<div class="form-group">
										<label for="company">Email Id</label> <input type="email"
											class="form-control" id="company" placeholder="${mail}"
											name="companyname" readonly="readonly">
									</div>
									<div class="form-group">
										<label for="hrcontact">Company Name</label> <input type="text"
											class="form-control" id="hrcontact" placeholder="${comp}"
											readonly="readonly" name="contact">
									</div>

									<div class="form-group">
										<label for="hrcontact">Contact Number</label> <input
											type="text" class="form-control" id="hrcontact"
											placeholder="${phone}" readonly="readonly" name="contact">
									</div>
									<div class="form-group">
										<label for="hrcontact">Eduinq ReCruinter ID</label> <input
											type="text" class="form-control" id="hrcontact"
											placeholder="${reqId}" readonly="readonly" name="contact">
									</div>
									<!-- Additional form fields can be added as needed -->
									<!-- <div class="form-group">
                  <label for="countryId">Country</label>
                  <select name="country" class="countries form-control" id="countryId">
                    <option value="">Select Country</option>
                  </select>
                </div>
                <div class="form-group">
                  <label for="city">City</label>
                  <input type="text" class="form-control" id="city" placeholder="City Name" name="city">
                </div> -->
									<button type="submit" class="btn btn-primary">Submit</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="trnsc" class="content">
		<header>
			<h1>Wallet Transaction History</h1>
		</header>

		<section>
			<div id="table-container">
				<table>
					<thead>
						<tr>
							<th>S.No</th>
							<th>Date</th>
							<th>Transaction Details</th>
						</tr>
					</thead>
					<tbody>${others}
					</tbody>
				</table>
			</div>
		</section>
		<div id="pagination-container"></div>
		<button id="refresh" type="button" class="btn btn-primary">Refresh</button>


	</div>
		
	<div id="recharge" class="content">
		<form action="razorpay" method="get">
	        <div class="form-group">
	            <input name="amount" type="number" class="form-control" placeholder="Enter amount" required>
	        	<button type="submit" class="btn btn-primary">Submit</button>
	        </div>
	    </form>
	</div>
	
	<div id="passReset" class="content">
		<div class="container">
	        <div class="row justify-content-center">
	            <div class="col-md-6 text-center mb-5">
	                <h2 class="heading-section">Eduinq Password Reset</h2>
	            </div>
	        </div>
	        <div class="row justify-content-center">
	            <div class="col-md-6 col-lg-4">
	                <div class="login-wrap p-0">
	                    <h3 class="mb-4 text-center">Reset Your Password</h3>
	                    <div class="alert-container">
	                        <!-- You can display password reset success or error messages here -->
	                    </div>
	                    <form action="passReset" method="post">
	                        <div class="form-group">
	                            <input name="email" type="email" class="form-control" placeholder="Email Address" required>
	                        </div>
	                        <div class="form-group">
	                            <button type="submit" class="form-control btn btn-primary submit px-3">Submit</button>
	                        </div>
	                    </form>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

	<script>
		function showPage(pageId) {
			// Hide all content divs
			const contentDivs = document.querySelectorAll('.content');
			contentDivs.forEach(div => div.style.display = 'none');
	
			// Show the selected content div
			document.getElementById(pageId).style.display = 'block';
		}
	
		showPage('${alert}');

		//Pagination
			//------configuration-------
			const itemsPerPage = 1;
			const maxPage = 3;
			const tableRows = document.querySelectorAll("#table-container tbody tr");
			//--------------------------
		
			const numPages = Math.ceil(tableRows.length / itemsPerPage);
			let currentPage = 0;
		
			function doPagination(page) {
		
				if (page >= 0 & page < numPages) {
		
					const startIndex = page * itemsPerPage;
					const endIndex = (page + 1) * itemsPerPage;
		
					//show data
					for (let i = 0; i < tableRows.length; i++) {
						if (i >= startIndex && i < endIndex) {
							tableRows[i].style.display = "table-row";
						} else {
							tableRows[i].style.display = "none";
						}
					}
		
					//edit buttons
					var paginationButtons = document.querySelectorAll(".pagination .numbered");
					console.log(paginationButtons);
		
					for (let i = 0; i < paginationButtons.length; i++) {
						if (page == (numPages - 1)) {
							paginationButtons[maxPage - i - 1].innerHTML = numPages - i;
							paginationButtons[maxPage - i - 1].id = numPages - i - 1;
						} else if (page + paginationButtons.length <= numPages) {
							paginationButtons[i].innerHTML = page + i + 1;
							paginationButtons[i].id = page + i;
						}
		
						if (document.getElementById(currentPage) != null) {
							document.getElementById(currentPage).classList.remove("active");
						}
						document.getElementById(page).classList.add("active");
					}
					currentPage = page;
		
				}
			}
		
			function setupPagination() {
		
				const pagination = document.createElement("div");
				pagination.className = "pagination";
		
				const ul = document.createElement("ul");
				
				const firstButton = document.createElement("button");
				firstButton.textContent = "<<";
				firstButton.addEventListener("click", () => doPagination(0));
				const firstLi = document.createElement("li");
				firstLi.appendChild(firstButton);
				ul.appendChild(firstLi);
		
				const prevButton = document.createElement("button");
				prevButton.textContent = "<";
				prevButton.addEventListener("click", () => doPagination(currentPage - 1));
				const prevLi = document.createElement("li");
				prevLi.appendChild(prevButton);
				ul.appendChild(prevLi);
		
				//numbered buttons
				let startPage;
				let endPage;
		
				if (numPages <= maxPage) {
					startPage = 0;
					endPage = numPages;
				} else {
					startPage = Math.max(currentPage - Math.floor(maxPage / 2), 0);
					endPage = Math.min(startPage + maxPage, numPages);
					if (endPage === numPages) {
						startPage = Math.max(endPage - maxPage + 1, 0);
					}
				}
		
				for (let i = startPage; i < endPage; i++) {
					const li = document.createElement("li");
					
					const pageButton = document.createElement("button");
					pageButton.classList.add("numbered");
					pageButton.setAttribute("id", i);
					pageButton.textContent = i + 1;
					pageButton.setAttribute("onClick", " doPagination(parseInt(this.id))");
					if (i === currentPage) {
						pageButton.classList.add("active");
					}
					li.appendChild(pageButton);
					ul.appendChild(li);
				}
		
				const nextButton = document.createElement("button");
				nextButton.textContent = ">";
				nextButton.addEventListener("click", () => doPagination(currentPage + 1));
				const nextLi = document.createElement("li");
				nextLi.appendChild(nextButton);
				ul.appendChild(nextLi);;
		
				const lastButton = document.createElement("button");
				lastButton.textContent = ">>";
				lastButton.addEventListener("click", () => doPagination(numPages - 1));
				const lastLi = document.createElement("li");
				lastLi.appendChild(lastButton);
				ul.appendChild(lastLi);
				
				pagination.appendChild(ul);
		
				document.querySelector("#pagination-container").appendChild(pagination);
			}
		
			setupPagination();
			doPagination(currentPage);
	</script>

</body>
</html>