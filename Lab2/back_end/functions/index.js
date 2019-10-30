const functions = require('firebase-functions');
const json = [    
    {      
        "hospitalBuilding": "True lies",     
        "ward": "1994",      
        "fullNamePatient": "Vitalii",      
        "pressure": "120",      
        "temperature": "36",  
        "palpitation":"60",    
        "photoUrl": "https://m.media-amazon.com/images/M/MV5BYzg5YmUyNGMtMThiNS00MjA2LTgwZDctNDlhM2RkZDNmZmRkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_UX182_CR0,0,182,268_AL_.jpg"    
    }, 
    {      
        "hospitalBuilding": "True lies",     
        "ward": "1994",      
        "fullNamePatient": "Roi",      
        "pressure": "120",      
        "temperature": "36",  
        "palpitation":"60",    
        "photoUrl": "https://m.media-amazon.com/images/M/MV5BYzg5YmUyNGMtMThiNS00MjA2LTgwZDctNDlhM2RkZDNmZmRkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_UX182_CR0,0,182,268_AL_.jpg"    
    },
    {      
        "hospitalBuilding": "True lies",     
        "ward": "1994",      
        "fullNamePatient": "Roi",      
        "pressure": "120",      
        "temperature": "36",  
        "palpitation":"60",    
        "photoUrl": "https://m.media-amazon.com/images/M/MV5BYzg5YmUyNGMtMThiNS00MjA2LTgwZDctNDlhM2RkZDNmZmRkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_UX182_CR0,0,182,268_AL_.jpg"    
    },  
    {      
        "hospitalBuilding": "True lies",     
        "ward": "1994",      
        "fullNamePatient": "Roi",      
        "pressure": "120",      
        "temperature": "36",  
        "palpitation":"60",    
        "photoUrl": "https://m.media-amazon.com/images/M/MV5BYzg5YmUyNGMtMThiNS00MjA2LTgwZDctNDlhM2RkZDNmZmRkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_UX182_CR0,0,182,268_AL_.jpg"    
    },
    {      
        "hospitalBuilding": "ADC",     
        "ward": "1994",      
        "fullNamePatient": "vITALII",      
        "pressure": "120",      
        "temperature": "36",  
        "palpitation":"60",    
        "photoUrl": "https://m.media-amazon.com/images/M/MV5BYzg5YmUyNGMtMThiNS00MjA2LTgwZDctNDlhM2RkZDNmZmRkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_UX182_CR0,0,182,268_AL_.jpg"    
    },
    {      
        "hospitalBuilding": "True lies",     
        "ward": "1994",      
        "fullNamePatient": "Roi",      
        "pressure": "120",      
        "temperature": "36",  
        "palpitation":"60",    
        "photoUrl":"https://m.media-amazon.com/images/M/MV5BMTYwOTEwNjAzMl5BMl5BanBnXkFtZTcwODc5MTUwMw@@._V1_UX182_CR0,0,182,268_AL_.jpg"
    }
     ];
     
     exports.hospital = functions.https.onRequest((request, response) => { response.send(json);});
