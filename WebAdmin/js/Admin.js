
var config = {

    apiKey: "AIzaSyB1JbA7uAgMRxTTTrDt164u0C06P2HlNCs",
    authDomain: "findbuffet-a597a.firebaseapp.com",
    databaseURL: "https://findbuffet-a597a.firebaseio.com",
    projectId: "findbuffet-a597a",
    storageBucket: "findbuffet-a597a.appspot.com",
    messagingSenderId: "626976600407"
};
firebase.initializeApp(config);
const db = firebase.firestore();
db.settings({ timestampsInSnapshots: true });
const form = document.querySelector('#add_restuarant');

var selectedFile = [];
upload.addEventListener('change', function (e) {
    for (var i = 0; i < e.target.files.length; i++) {
        selectedFile[i] = event.target.files[i];
    }
});


form.addEventListener('submit', (e) => {
    e.preventDefault();
    var num = 0;
    var name = [];
    var uurl = [];
    for (var i = 0; i < selectedFile.length; i++) {
        var metadata = {
            contentType: 'image',
            customMetadata: {
                'name_th': $("#name_th").val(),
                'name_en': $("#name_en").val(),
            },
        };
        var uploadTask = firebase.storage().ref().child(form.name_eng.value + '/' + selectedFile[i].name).put(selectedFile[i], metadata)
        localStorage.setItem('name' + i, form.name_eng.value + '/' + selectedFile[i].name)


        uploadTask.on('state_changed', function (snapshot) {
        }, function (error) {
        }, function () {
            num++;
            var list = [];


            if (num == selectedFile.length) {
                q = 0;
                for (var j = 0; j < num; j++) {

                    var storageRef = firebase.storage().ref();

                    storageRef.child(localStorage.getItem('name' + j)).getDownloadURL().then(function (url1) {


                        list[q] = url1
                        q++;
                        if (q == num) {
                            console.log(list)
                            test(list);

                        }
                    }).catch(function (error) {
                    })
                }
            }
        });
    }


});


function test(list) {

    if (document.getElementById("type1").checked) {
        db.collection('Restuarant_Buffet').doc("seafood").collection("seafood").doc(form.name_eng.value).set({
            name_th: form.name_th.value,
            name_en: form.name_eng.value,
            address: form.address.value,
            telephone: form.telephone.value,
            time: form.time.value,
            lat: form.lat.value,
            lng: form.lng.value,
            image_url: list
        });

    }
    if (document.getElementById("type2").checked) {
        db.collection('Restuarant_Buffet').doc("dessert").collection("dessert").doc(form.name_eng.value).set({
            name_th: form.name_th.value,
            name_en: form.name_eng.value,
            address: form.address.value,
            telephone: form.telephone.value,
            time: form.time.value,
            lat: form.lat.value,
            lng: form.lng.value,
            image_url: list
        });
    }
    if (document.getElementById("type3").checked) {
        db.collection('Restuarant_Buffet').doc("japanese").collection("japanese").doc(form.name_eng.value).set({
            name_th: form.name_th.value,
            name_en: form.name_eng.value,
            address: form.address.value,
            telephone: form.telephone.value,
            time: form.time.value,
            lat: form.lat.value,
            lng: form.lng.value,
            image_url: list
        });

    }
    if (document.getElementById("type4").checked) {
        db.collection('Restuarant_Buffet').doc("beef").collection("beef").doc(form.name_eng.value).set({
            name_th: form.name_th.value,
            name_en: form.name_eng.value,
            address: form.address.value,
            telephone: form.telephone.value,
            time: form.time.value,
            lat: form.lat.value,
            lng: form.lng.value,
            image_url: list
        });
    }
    if (document.getElementById("type5").checked) {
        db.collection('Restuarant_Buffet').doc("thai").collection("thai").doc(form.name_eng.value).set({
            name_th: form.name_th.value,
            name_en: form.name_eng.value,
            address: form.address.value,
            telephone: form.telephone.value,
            time: form.time.value,
            lat: form.lat.value,
            lng: form.lng.value,
            image_url: list
        });

    }
    if (document.getElementById("type6").checked) {
        db.collection('Restuarant_Buffet').doc("other").collection("other").doc(form.name_eng.value).set({
            name_th: form.name_th.value,
            name_en: form.name_eng.value,
            address: form.address.value,
            telephone: form.telephone.value,
            time: form.time.value,
            lat: form.lat.value,
            lng: form.lng.value,
            image_url: list
        });
    }

    var delayInMilliseconds = 1500; //1 second

    setTimeout(function () {
        window.location.href = "index.html";
    }, delayInMilliseconds);

    

}
