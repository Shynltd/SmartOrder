var mongoose = require('mongoose');
var userSchema = new mongoose.Schema({
    userName: {
        type: String,
        required: true,
    },
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
    soCMND: {
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
        type:String,
        required: true
    },
    age: {
        type:Number,
        required: true
    }
})
module.exports = mongoose.model('User', userSchema, 'user');