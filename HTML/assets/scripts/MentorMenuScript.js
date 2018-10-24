var lastPage = "mentorWelcomePage.html";

var codecoolerNodes = document.getElementById("codecoolerOptionsList").childNodes;
for(let i = 0; i < codecoolerNodes.length; i++) {
    codecoolerNodes[i].onmouseout = function() { 
    document.getElementById("inner").src = lastPage;
    }
    codecoolerNodes[i].onmouseover = function() {
        document.getElementById("inner").src = codecoolerNodes[i].getAttribute("data-source"); 
    }
    codecoolerNodes[i].onclick = function() {
        document.getElementById("inner").src = codecoolerNodes[i].getAttribute("data-source");
        lastPage = codecoolerNodes[i].getAttribute("data-source");
    }
};

var shopNodes = document.getElementById("shopOptionsList").childNodes;
for(let j = 0; shopNodes.length; j++) {
    shopNodes[j].onmouseout = function() {
        document.getElementById("inner").src = lastPage;
    }
    shopNodes[j].onmouseover = function() {
        document.getElementById("inner").src = shopNodes[j].getAttribute("data-source");
    }
    shopNodes[j].onclick = function() {
        document.getElementById("inner").src = shopNodes[j].getAttribute("data-source");
        lastPage = shopNodes[i].getAttribute("data-source");
    }
};

var questNodes = document.getElementById("questOptionsList").childNodes;
for(let k = 0; questNodes.length; k++) {
    questNodes[k].onmouseout = function() {
        document.getElementById("inner").src = lastPage;
    }
    questNodes[k].onmouseover = function() {
        document.getElementById("inner").src = questNodes[k].getAttribute("data-source");
    }
    questNodes[k].onclick = function() {
        document.getElementById("inner").src = questNodes[k].getAttribute("data-source");
        lastPage = questNodes[i].getAttribute("data-source");
    }
};
