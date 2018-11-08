var lastPage = "codecoolerMain.html";

var codecoolerNodes = document.getElementById("codecoolerMenu").childNodes;
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
}