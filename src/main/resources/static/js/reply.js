async function get1(id) {

    const result = await axios.get(`/replies/list/${id}`)

    return result;
}
async function getList({id, page, size, goLast}){
    const result = await axios.get(`/replies/list/${id}`, {params: {page, size}})

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({id:id, page:lastPage, size:size})
    }
    return result.data
}

async function addReply(replyObj) {
    const response = await axios.post(`/replies/`,replyObj)
    return response.data
}

async function getReply(rid) {
    const response = await axios.get(`/replies/${rid}`)
    return response.data
}

async function modifyReply(replyObj) {

    const response = await axios.put(`/replies/${replyObj.rid}`, replyObj)
    return response.data
}

async function removeReply(rid) {
    const response = await axios.delete(`/replies/${rid}`)
    return response.data
}