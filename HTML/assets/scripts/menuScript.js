var handlerClicked = false;
var pageSwitchedAtLastOnce = false;

function addListener(){
  addClickListeners();
  
  addMouseOverListeners();
  
  addMouseOutListeners();
}

function addClickListeners(){
  document.getElementById("mentorHandler").addEventListener("click", switchToMentor);

  document.getElementById("classHandler").addEventListener("click", switchToClass);

  document.getElementById("expHandler").addEventListener("click", switchToExp);
}

function addMouseOverListeners(){
  document.getElementById("mentorHandler").addEventListener("mouseover", showMentor);

  document.getElementById("classHandler").addEventListener("mouseover", showClass);

  document.getElementById("expHandler").addEventListener("mouseover", showExp);
}

function addMouseOutListeners(){
  document.getElementById("mentorHandler").addEventListener("mouseout", mouseOutHandling);

  document.getElementById("classHandler").addEventListener("mouseout", mouseOutHandling);

  document.getElementById("expHandler").addEventListener("mouseout", mouseOutHandling);
}

function switchToMentor(){
  document.getElementById("inner").src = "mentorHandler.html";
  if(!handlerClicked){
  handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function switchToClass(){
  document.getElementById("inner").src = "classHandler.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function switchToExp(){
  document.getElementById("inner").src = "expHandler.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function showMentor(){
  if (!window.handlerClicked){
  document.getElementById("inner").src = "mentorHandler.html";
  }
}

function showClass(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "classHandler.html";
  }
}

function showExp(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "expHandler.html";
  }
}

function mouseOutHandling(){
  if (!handlerClicked && !pageSwitchedAtLastOnce){
    document.getElementById("inner").src = "greetAdmin.html";
  }
}

