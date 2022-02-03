var app = new Vue({
    el:"#app",
    data:{
        clientInfo: {},
        error: null,
        errorMsg: ''
    },
    methods:{
        getData: function(){
            axios.get("/api/clients/current")
                .then(function (response) {
                    //get client ifo
                    app.clientInfo = response.data;
                })
                .catch(function (error) {
                    // handle error
                    app.error = error;
                })
        },
        formatDate: function(date){
            return new Date(date).toLocaleDateString('en-gb');
        },
        create: function (){
            axios.post("http://localhost:8080/api/clients/current/accounts")
                .then(response => window.location.reload())
                .catch((error) =>{
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
        }
    },
    mounted: function(){
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
})