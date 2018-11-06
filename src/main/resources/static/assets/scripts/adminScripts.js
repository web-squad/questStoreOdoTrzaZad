var handlerClicked = false;
var pageSwitchedAtLastOnce = false;

function addListener(){
  addClickListeners();
  
  addMouseOverListeners();
  
  addMouseOutListeners();
}

function addClickListeners(){
  addMentorHandlerListeners();

  addClassHandlerListeners();

  addEXPHandlerListeners();
}

function addMouseOverListeners(){
  addMentorHandlerMOVListeners();

  addClassHandlerMOVListeners();

  addEXPHandlerMOVListeners();
}

function addMouseOutListeners(){
  addMentorHandlerMOUTListeners();

  addClassHandlerMOUTListeners();

  addEXPHandlerMOUTListeners();
}

function addMentorHandlerListeners(){
  document.getElementById("mentorAdder").addEventListener("click", switchToMentorAdder);

  document.getElementById("mentorEditor").addEventListener("click", switchToMentorEditor);

  document.getElementById("mentorDeleter").addEventListener("click", switchToMentorDeleter);
}

function addClassHandlerListeners(){
  document.getElementById("classAdder").addEventListener("click", switchToClassAdder);

  document.getElementById("classEditor").addEventListener("click", switchToClassEditor);

  document.getElementById("classDeleter").addEventListener("click", switchToClassDeleter);
}

function addEXPHandlerListeners(){
  document.getElementById("expLVLAdder").addEventListener("click", switchToexpLVLAdder);

  document.getElementById("expLVLEditor").addEventListener("click", switchToexpLVLEditor);

  document.getElementById("expLVLDeleter").addEventListener("click", switchToexpLVLDeleter);
}

function addMentorHandlerMOVListeners(){
  document.getElementById("mentorAdder").addEventListener("mouseover", showMentorAdder);

  document.getElementById("mentorEditor").addEventListener("mouseover", showMentorEditor);

  document.getElementById("mentorDeleter").addEventListener("mouseover", showMentorDeleter);
}

function addClassHandlerMOVListeners(){
  document.getElementById("classAdder").addEventListener("mouseover", showClassAdder);

  document.getElementById("classEditor").addEventListener("mouseover", showClassEditor);

  document.getElementById("classDeleter").addEventListener("mouseover", showClassDeleter);
}

function addEXPHandlerMOVListeners(){
  document.getElementById("expLVLAdder").addEventListener("mouseover", showExpAdder);

  document.getElementById("expLVLEditor").addEventListener("mouseover", showExpEditor);

  document.getElementById("expLVLDeleter").addEventListener("mouseover", showExpDeleter);
}

function addMentorHandlerMOUTListeners(){
  document.getElementById("mentorAdder").addEventListener("mouseout", mouseOutHandling);

  document.getElementById("mentorEditor").addEventListener("mouseout", mouseOutHandling);

  document.getElementById("mentorDeleter").addEventListener("mouseout", mouseOutHandling);
}

function addClassHandlerMOUTListeners(){
  document.getElementById("classAdder").addEventListener("mouseout", mouseOutHandling);

  document.getElementById("classEditor").addEventListener("mouseout", mouseOutHandling);

  document.getElementById("classDeleter").addEventListener("mouseout", mouseOutHandling);
}

function addEXPHandlerMOUTListeners(){
  document.getElementById("expLVLAdder").addEventListener("mouseout", mouseOutHandling);

  document.getElementById("expLVLEditor").addEventListener("mouseout", mouseOutHandling);

  document.getElementById("expLVLDeleter").addEventListener("mouseout", mouseOutHandling);
}

function switchToMentorAdder(){
  document.getElementById("inner").src = "mentorAdder.html";
  if(!handlerClicked){
  handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function switchToMentorEditor(){
  document.getElementById("inner").src = "mentorEditor.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function switchToMentorDeleter(){
  document.getElementById("inner").src = "mentorDeleter.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function switchToClassAdder(){
  document.getElementById("inner").src = "classAdder.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function switchToClassEditor(){
  document.getElementById("inner").src = "classEditor.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function switchToClassDeleter(){
  document.getElementById("inner").src = "classDeleter.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}


function switchToexpLVLAdder(){
  document.getElementById("inner").src = "expLVLAdder.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function switchToexpLVLEditor(){
  document.getElementById("inner").src = "expLVLEditor.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function switchToexpLVLDeleter(){
  document.getElementById("inner").src = "expLVLDeleter.html";
  if(!handlerClicked){
    handlerClicked = true;  
  }
  else{
    handlerClicked = false;
  }
  pageSwitchedAtLastOnce = true;
}

function showMentorDeleter(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "mentorDeleter.html";
  }
}

function showMentorAdder(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "mentorAdder.html";
  }
}

function showMentorEditor(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "mentorEditor.html";
  }
}

function showClassDeleter(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "classDeleter.html";
  }
}


function showClassEditor(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "classDeleter.html";
  }
}

function showClassAdder(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "classAdder.html";
  }
}

function showExpEditor(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "expLVLEditor.html";
  }
}

function showExpDeleter(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "expLVLDeleter.html";
  }
}

function showExpAdder(){
  if (!window.handlerClicked){
    document.getElementById("inner").src = "expLVLAdder.html";
  }
}

function mouseOutHandling(){
  if (!handlerClicked && !pageSwitchedAtLastOnce){
    document.getElementById("inner").src = "greetAdmin.html";
  }
}

function openMainPage(){
  document.getElementById("inner").src = "greetAdmin.html";
}


