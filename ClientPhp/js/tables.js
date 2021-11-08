
function ajouterEvent() {
    let add=document.querySelectorAll(".ajouterProduit");
    let gauche=document.getElementById("gauche");

    add.forEach((el)=> {
        el.addEventListener("click", function() {
            addProduit(el);
        });
    });
    let supprimer=document.querySelectorAll(".imgdelete");
    supprimer.forEach((el)=> {
        el.addEventListener("click", function() {
            supprimerProduit(el);
        });
    });

    let modif=document.getElementById("modifNbPersonne");
    modif.addEventListener("click",function(){
        if (modif.getAttribute("value","Modifier")=="Modifier") {
            modif.previousElementSibling.remove();
            let input=document.createElement("input");
            input.setAttribute("type","text")
            input.setAttribute("id","inputFondBlanc")
            modif.parentNode.append(input);
            modif.setAttribute("value","Valider")
        }
        else {
            changeNbPlaces()
            addCommand()
            let elementParent=modif.parentNode
            modif.setAttribute("value","Modifier")
            let input=document.createElement("input");
            let inputhidden1=document.createElement("input")
            let inputhidden2=document.createElement("input")
            let p = document.createElement("p")
            p.innerHTML=document.getElementById("inputFondBlanc").value
            elementParent.append(p)
            input.setAttribute("id","modifNbPersonne")
            input.setAttribute("type","button")
            input.setAttribute("value","Modifier")
            inputhidden2.setAttribute("id","idTable")
            inputhidden2.setAttribute("value",document.getElementById("idTable").value)
            inputhidden2.setAttribute("type","hidden")
            inputhidden1.setAttribute("id","nbPlaces")
            inputhidden1.setAttribute("value",document.getElementById("inputFondBlanc").value)
            inputhidden1.setAttribute("type","hidden")
            elementParent.innerHTML="";
            elementParent.append(p)
            elementParent.append(input)
            elementParent.append(inputhidden2)
            elementParent.append(inputhidden1)
            ajouterEvent()
        }
    })
}


function addProduit(event){
    let requestPost=new XMLHttpRequest();

    let object= new Object();
    object.idCommande=1;
    object.idProduit=1;
    object.quantite=1;
    let jsonencode=JSON.stringify(object);

    requestPost.addEventListener("readystatechange",function(){
        if (requestPost.readyState== 4) {
            if (requestPost.status == 200) {
                getProduit(event.alt)
            }
            else {
                console.log(requestPost.status);
            }
        }
    });

    requestPost.open("POST","http://localhost/pi-zza-Groupe-1-/server/contient");
    requestPost.send(jsonencode);}

function supprimerProduit(event){
    let requestPost=new XMLHttpRequest();
    let idProduit=event.alt;
    requestPost.addEventListener("readystatechange",function(){
        if (requestPost.readyState== 4) {
            if (requestPost.status == 200) {
                let parent=event.parentNode
                console.log(parent.parentNode)
                parent.parentNode.remove();
            }
            else {
                console.log(requestPost.status);
            }
        }
    });

    requestPost.open("DELETE","http://localhost/pi-zza-Groupe-1-/server/contient/"+idProduit);
    requestPost.send();}


function getProduit(id){
    let requestPost=new XMLHttpRequest();
    requestPost.addEventListener("readystatechange",function(){
        if (requestPost.readyState== 4) {
            if (requestPost.status == 200) {
                var jsonobj=requestPost.responseText;
                var obj=JSON.parse(jsonobj);
                let divproduit=document.createElement("div");
                divproduit.setAttribute("class","produit");
                    let divligne=document.createElement("div");
                    divligne.setAttribute("class","ligne");
                    divproduit.appendChild(divligne)

                        let img=document.createElement("img");
                        img.setAttribute("src",obj.imageProduit);
                        divligne.appendChild(img)

                        let colonne1=document.createElement("div");
                            colonne1.setAttribute("class","colonne");
                            let nom=document.createElement("p");
                            nom.innerText="Nom";
                            let nomProduit=document.createElement("p");
                            nomProduit.innerHTML=obj.nomProduit
                            colonne1.appendChild(nom);
                            colonne1.appendChild(nomProduit)
                            divligne.appendChild(colonne1)


                        let colonne2=document.createElement("div");
                            colonne2.setAttribute("class","colonne");
                            let prix=document.createElement("p");
                            prix.innerHTML="Prix";
                            let prixProduit=document.createElement("p");
                            prixProduit.innerHTML=obj.prixProduit
                            colonne2.appendChild(prix)
                            colonne2.appendChild(prixProduit)
                            divligne.appendChild(colonne2)

                        let colonne3=document.createElement("div");
                            colonne3.setAttribute("class","colonne");
                            let quantite=document.createElement("p");
                            quantite.innerHTML="Quantite";
                            let quantiteProduit=document.createElement("p");
                            quantiteProduit.innerHTML="1";
                            colonne3.appendChild(quantite)
                            colonne3.appendChild(quantiteProduit)
                            divligne.appendChild(colonne3)

                        let divdelete = document.createElement("div");
                            divdelete.setAttribute("class","delete");
                            let imgDelete=document.createElement("img")
                            imgDelete.setAttribute("class","imgdelete");
                            imgDelete.setAttribute("src","img/delete.png");
                            imgDelete.setAttribute("alt",obj.idProduit)
                            divdelete.appendChild(imgDelete)
                            divligne.appendChild(divdelete)
                            divdelete.addEventListener("click", function() {
                                supprimerProduit(divdelete)
                            });

                gauche.appendChild(divproduit)
                ajouterEvent()
            }
            else {
                console.log(requestPost.status);
            }
        }
    });

    requestPost.open("GET","http://localhost/pi-zza-Groupe-1-/server/produit/"+id);
    requestPost.send();
}
function changeNbPlaces() {

    let object= new Object();
    object.idTables=document.getElementById("idTable").value;
    object.nbPlaces=document.getElementById("nbPlaces").value;
    object.nbPersonne=document.getElementById("inputFondBlanc").value;

    let jsonencode=JSON.stringify(object);
    console.log(jsonencode)
    let requestPost=new XMLHttpRequest();
    requestPost.addEventListener("readystatechange",function(){
        if (requestPost.readyState== 4) {
            if (requestPost.status == 200) {
            }
            else {
                console.log(requestPost.status);
            }
        }
    });

    requestPost.open("PUT","http://localhost/pi-zza-Groupe-1-/server/tables");
    requestPost.send(jsonencode);
}

function addCommand() {
    let object= new Object();
    object.idTables=document.getElementById("idTable").value;
    let jsonencode=JSON.stringify(object);
    console.log(jsonencode)
    let requestPost=new XMLHttpRequest();
    requestPost.addEventListener("readystatechange",function(){
        if (requestPost.readyState== 4) {
            if (requestPost.status == 200) {

            }
            else {
                console.log(requestPost.status);
            }
        }
    });

    requestPost.open("POST","http://localhost/pi-zza-Groupe-1-/server/commande");
    requestPost.send(jsonencode);

}


ajouterEvent();