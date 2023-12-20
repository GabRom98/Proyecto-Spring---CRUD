export async function buscarPorId(idPais) {
    const urlService = "/api/pais/";
    const urlServiceBuscarPorId = urlService + idPais;
    const data = await fetch(urlServiceBuscarPorId);
    return data.ok ? await data.json() : Promise.reject(response);
}