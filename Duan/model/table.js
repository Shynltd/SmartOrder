let mongoose = require('mongoose');
let tableSchema = new mongoose.Schema({
    tableCode: {
        type: Number,
        required: true,
    },
    tableSeats: {
        type: Number,
        required:true
    }
})
module.exports = mongoose.model('table', tableSchema, 'table');