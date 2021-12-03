window.onload=function()
{
    function addsupprevent(){
        let supprimer=document.querySelectorAll(".imgdelete");
        supprimer.forEach((el)=> {
            el.addEventListener("click", function() {
                supprimerProduit(el);
            });
        });
    };
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


    function addProduit(event)
    {
        getProduit(event.alt,1)
    }

    function supprimerProduit(event)
    {
        let parent=event.parentNode.parentNode
        let gauche=document.querySelector("div#gauche");
        gauche.removeChild(parent);
        parent.setAttribute("class","del");
        let child=parent.querySelector('div.ligne2');
        parent.removeChild(child);
        gauche.appendChild(parent);
    }

    function getProduit(id,quantites){
        if (document.getElementById("idProduit"+id)) {
            updateQuantite(id)
        }
        else {
            let requestPost=new XMLHttpRequest();
            requestPost.addEventListener("readystatechange",function(){
                if (requestPost.readyState== 4) {
                    if (requestPost.status == 200) {
                        var jsonobj=requestPost.responseText;
                        var obj=JSON.parse(jsonobj);
                        let divproduit=document.createElement("div");
                        divproduit.setAttribute("class","produit");
                        divproduit.setAttribute("id","idProduit"+id)
                        let divligne=document.createElement("div");
                        divligne.setAttribute("class","ligne2");
                        divproduit.appendChild(divligne)

                        let img=document.createElement("img");
                        img.setAttribute("src","../Client/Pi_zza/"+obj.imageProduit);
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
                        prixProduit.innerHTML=obj.prixProduit+"€"
                        colonne2.appendChild(prix)
                        colonne2.appendChild(prixProduit)
                        divligne.appendChild(colonne2)

                        let colonne3=document.createElement("div");
                        colonne3.setAttribute("class","colonne");
                        let quantite=document.createElement("p");
                        quantite.innerHTML="Quantite";
                        let quantiteProduit=document.createElement("p");
                        quantiteProduit.setAttribute("id","quantiteProduit"+id)
                        quantiteProduit.innerHTML=quantites;
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
                    }
                    else {
                        console.log(requestPost.status);
                    }
                }
                addsupprevent()
            });

            requestPost.open("GET","http://localhost/pi-zza-Groupe-1-/server/produit/"+id);
            requestPost.send();
        }
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

    function addCommand() 
    {
        let nouv=document.querySelector("div.nouv")
        if(nouv)
        {
            let object=new Object();
            object.idTables=document.getElementById("idTable").value;
            let jsonencode=JSON.stringify(object);
            console.log(jsonencode)
            let requestPost=new XMLHttpRequest();
            requestPost.addEventListener("readystatechange",function(){
                if (requestPost.readyState== 4) 
                {
                    if (requestPost.status == 200) 
                    {
                        supprContient();
                        addContient();
                    }
                    else 
                    {
                        console.log(requestPost.status);
                    }
                }
            });

            requestPost.open("POST","http://localhost/pi-zza-Groupe-1-/server/commande");
            requestPost.send(jsonencode);
        }
    }
    function updateQuantite(idProduit) {
        let qteProduit=document.getElementById("quantiteProduit"+idProduit).innerHTML
        let produit=document.getElementById("quantiteProduit"+idProduit)
        let object= new Object();
        object.idCommande=1;
        object.idProduit=idProduit;
        object.quantite=parseInt(qteProduit)+1;
        let jsonencode=JSON.stringify(object);
        let requestPost=new XMLHttpRequest();
        requestPost.addEventListener("readystatechange",function(){
            if (requestPost.readyState== 4) {
                if (requestPost.status == 200) {
                    produit.innerHTML=object.quantite
                }
                else {
                    console.log(requestPost.status);
                }
            }
        });
        requestPost.open("PUT","http://localhost/pi-zza-Groupe-1-/server/contient");
        requestPost.send(jsonencode);
    }
    function update(id){
        let produit=document.querySelectorAll("div.ligne");
        let conteneur=document.querySelector("div.droitetable");
        produit.forEach((el)=>{
            conteneur.removeChild(el);
        });
        if(id==null)
        {
            let img=document.createElement("img");
            img.setAttribute("src","img/logo.png");
            conteneur.appendChild(img)
        }
        let request = new XMLHttpRequest();
            request.addEventListener("readystatechange", function () {
                if (this.readyState == 4 && this.status == 200) {
                    var jsonobj=request.responseText;
                    var objet=JSON.parse(jsonobj);
                    objet.forEach((obj)=>
                    {
                        let divligne=document.createElement("div");
                        divligne.setAttribute("class","ligne");
                        conteneur.appendChild(divligne)

                        let img=document.createElement("img");
                        img.setAttribute("src","../Client/Pi_zza/"+obj.imageProduit);
                        divligne.appendChild(img)

                        let colonne=document.createElement("div");
                        colonne.setAttribute("class","colonne");
                        let nomProduit=document.createElement("p");
                        nomProduit.innerHTML=obj.nomProduit
                        let prix=document.createElement("p");
                        prix.innerHTML=obj.prixProduit+"€";
                        colonne.appendChild(nomProduit);
                        colonne.appendChild(prix);
                        divligne.appendChild(colonne);
                        let img2=document.createElement("img");
                        img2.setAttribute("class","ajouterProduit");
                        img2.setAttribute("src","img/add.png");
                        img2.setAttribute("alt",obj.idProduit);
                        divligne.appendChild(img2)
                    });
                    ajouterEvent();
            }

                else {
                }
            });
                request.open("GET","http://localhost/pi-zza-Groupe-1-/server/produit/categorie/"+id);
                request.send();
    }
    function getlastid()
    {
        let requestPost1=new XMLHttpRequest();
        requestPost1.addEventListener("readystatechange",function(){
            if (requestPost1.readyState== 4) 
            {
                if (requestPost1.status == 200) 
                {
                    let jsonobj=requestPost1.responseText;
                    let objet=JSON.parse(jsonobj);
                    let divligne=document.createElement("div");
                    divligne.setAttribute("class","result");
                    divligne.setAttribute("id",objet.idCommande);
                    let conteneur=document.querySelector("div#gauche")
                    conteneur.appendChild(divligne)
                }
                else 
                {
                    console.log(requestPost1.status);
                }
            }
        });
        requestPost1.open("GET","http://localhost/pi-zza-Groupe-1-/server/commande/lastid");
        requestPost1.send();
    }
    function addContient()
    {
        var idcommande=document.querySelector("div.result");
        let produitpanier=document.querySelectorAll("div.produit");
        produitpanier.forEach((el)=>
        {
            let object= new Object();
            object.idCommande=idcommande.id;
            object.idProduit=el.id.substring(9);
            object.quantite=document.querySelector("p#quantiteProduit"+object.idProduit).innerHTML;
            let jsonencode=JSON.stringify(object);
            let requestPost=new XMLHttpRequest();
            requestPost.addEventListener("readystatechange",function(){
            if (requestPost.readyState== 4) 
            {
                if (requestPost.status == 200) 
                {
                    
                }
                else {
                    console.log(requestPost1.status);
                }
            }
        });
        requestPost.open("POST","http://localhost/pi-zza-Groupe-1-/server/contient");
        requestPost.send(jsonencode);
        });
    }
    function valide()
    {
        addCommand();
        returner();
    }
    function selectCommandeEvent()
    {
        var Commande=document.querySelectorAll("div.commande");
        Commande.forEach((el)=> 
        {
            el.addEventListener("click", function() 
            {
                selectCommande(el.id);
            });
        });
    }
    function selectCommande(id)
    {
        let Commande=document.querySelectorAll("div.lignecommande");
        let commande=document.querySelector("div.commande");
        let Gauche=document.querySelector("div#gauche");
        Gauche.removeChild(commande);
        Commande.forEach((el)=> 
        {
            Gauche.removeChild(el);
        });
        let cache=document.querySelectorAll("div#hidden");
        cache.forEach((el)=> 
        {
            el.setAttribute("id", "hid");
        });
        if (typeof(id)=='string' && id!="")
        {
            let idcommande=document.querySelector("div.result");
            idcommande.setAttribute("id",id)
        }
        else if(id=="")
        {
            let divligne=document.createElement("div");
            divligne.setAttribute("class","nouv");
            let conteneur=document.querySelector("div#gauche")
            conteneur.appendChild(divligne)
        }
        let requestPost=new XMLHttpRequest();
        requestPost.addEventListener("readystatechange",function(){
            if (requestPost.readyState== 4) 
            {
                if (requestPost.status == 200) 
                {
                    let objet=JSON.parse(requestPost.responseText);
                    objet.forEach((obj)=>
                    {
                        getProduit(obj.idProduit,obj.quantite);
                    });
                }
                else 
                {
                    console.log(requestPost.status);
                }
            }
        });
        requestPost.open("GET","http://localhost/pi-zza-Groupe-1-/server/contient/commande/"+id);
        requestPost.send();
    }
    function supprcommande(el)
    {
        let gauche=document.querySelector("div#gauche");
        gauche.removeChild(el.parentNode);
        let requestPost=new XMLHttpRequest();
        requestPost.addEventListener("readystatechange",function(){
            if (requestPost.readyState== 4) 
            {
                if (requestPost.status == 200) 
                {
                    
                }
                else 
                {
                    console.log(requestPost.status);
                }
            }
        });
        requestPost.open("DELETE","http://localhost/pi-zza-Groupe-1-/server/commande/"+el.parentNode.id);
        requestPost.send();
    }
    function supprContient()
    {
        var idcommande=document.querySelector("div.result");
        let produitpanier=document.querySelectorAll("div.del");
        produitpanier.forEach((el)=>
        {
            let object= new Object();
            object.idCommande=idcommande.id;
            object.idProduit=el.id.substring(9);
            let jsonencode=JSON.stringify(object);
            console.log(jsonencode);
            let requestPost=new XMLHttpRequest();
            requestPost.addEventListener("readystatechange",function(){
            if (requestPost.readyState== 4) 
            {
                if (requestPost.status == 200) 
                {
                    
                }
                else {
                    console.log(requestPost1.status);
                }
            }
        });
        requestPost.open("DELETE","http://localhost/pi-zza-Groupe-1-/server/contient/"+object.idCommande+"/"+object.idProduit);
        requestPost.send();
        });
    }
    function returner()
    {
        let produit=document.querySelectorAll("div.produit");
        let gauche=document.querySelector("div#gauche");
        let valid=document.querySelector("div.valider");
        gauche.removeChild(valid);
        gauche.removeChild(document.querySelector("div.result"));
        gauche.removeChild(document.querySelector("br"));
        produit.forEach((el)=>
        {
            gauche.removeChild(el)
        });
        let hidden=document.querySelectorAll("div#hid");
        hidden.forEach((el)=>
        {
            el.setAttribute("id", "hidden");
        });
        let divligne=document.createElement("div");
        divligne.setAttribute("class","commande");
        let p=document.createElement("p");
        p.innerHTML="Nouvelle Commande"
        divligne.appendChild(p)
        gauche.appendChild(divligne)
        gauche.appendChild(document.createElement("br"))
        let requestPost=new XMLHttpRequest();
        requestPost.addEventListener("readystatechange",function(){
            if (requestPost.readyState== 4) 
            {
                if (requestPost.status == 200) 
                {
                    let objet=JSON.parse(requestPost.responseText);
                    objet.forEach((obj)=>
                    {
                        let divmaster=document.createElement("div");
                        divmaster.setAttribute("class","lignecommande");
                        divmaster.setAttribute("id",obj.idCommande);
                        let divligne=document.createElement("div");
                        divligne.setAttribute("class","commande");
                        divligne.setAttribute("id",obj.idCommande);
                        let divtrash=document.createElement("div");
                        divtrash.setAttribute("class","trash");
                        let i=document.createElement("i");
                        i.setAttribute("class","fas fa-trash-alt");
                        let p=document.createElement("p");
                        p.innerHTML="Commande N°"+obj.idCommande;
                        divligne.appendChild(p);
                        divtrash.appendChild(i);
                        divmaster.appendChild(divligne);
                        divmaster.appendChild(divtrash);
                        gauche.appendChild(divmaster);
                    });
                    let divvalid=document.createElement("div");
                    divvalid.setAttribute("class","valider");
                    divvalid.setAttribute("id","hidden");
                    let p2=document.createElement("p");
                    p2.innerHTML="Valider";
                    divvalid.appendChild(p2);
                    gauche.appendChild(divvalid);
                    selectCommandeEvent();
                    addValide();
                    getlastid();
                }
                else 
                {
                    console.log(requestPost.status);
                }
            }
        });
        requestPost.open("GET","http://localhost/pi-zza-Groupe-1-/server/commande/tables/"+document.getElementById("idTable").value);
        requestPost.send();
    }
    function addValide()
    {
        let valid=document.querySelector("div.valider")
        valid.addEventListener("click",function()
        {
            valide();
        });
    }
    let form = document.querySelector("select#categorie");
    let id=null;
    form.addEventListener("click",function(){
        let option = document.querySelectorAll("option");
        option.forEach((op)=>{
           if (op.value==form.value)
           {
               id=op.id;
           }
        });
            update(id);
    });
    let suppr=document.querySelectorAll("div.trash")
    {
        suppr.forEach((el)=> 
        {
            el.addEventListener("click", function() 
            {
                supprcommande(el);
            });
        });
    }
    ajouterEvent();
    selectCommandeEvent();
    addValide();
    getlastid();
    update(id);
}
