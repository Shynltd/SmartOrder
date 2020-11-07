let mongoose = require('mongoose');
let userSchema = new mongoose.Schema({
    passWord: {
        type: String,
        required: true,
    },
    role: {
        type: String,
        required: true,
    },
    fullName: {
        type: String,
        required: true,
    },
    indentityCardNumber: {
        type: Number,
        required: true,
    },
    phone: {
        type: String,
        required: true
    },
    address: {
        type:String,
        required: true
    },
    avatar: {
        type:String
    },
    age: {
        type:Number,
        required: true
    }
})
module.exports = mongoose.model('User', userSchema, 'user');