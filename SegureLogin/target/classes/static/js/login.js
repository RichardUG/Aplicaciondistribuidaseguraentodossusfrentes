var api = (function () {
    var url="https://"+(window.location.href).split("/")[2];
    console.log(url);

    function otherService() {
        console.log("llega");
        axios.get(url+"/successful").then(res=>{
            console.log(url+"/successful")
            $("#information").text(res.data);
        })
    }
    function informacion(){
        window.alert("Digite su usuario y contraseña");
    }
    function login()  {
        console.log("url");
        console.log(url);
        var user={userName:document.getElementById("userName").value,password:document.getElementById("password").value}
        axios.post(url+"/login",user).then(api.otherService);
    }

    return {
        login:login,
        otherService:otherService,
        informacion:informacion
    };
})();