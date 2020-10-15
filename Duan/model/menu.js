var mongoose = require('mongoose');
var menuSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
    },
    price: {
        type: Number,
        required: true,
    },
    image: {
        type: String
    }
})
module.exports = mongoose.model('Menu', menuSchema, 'menu');