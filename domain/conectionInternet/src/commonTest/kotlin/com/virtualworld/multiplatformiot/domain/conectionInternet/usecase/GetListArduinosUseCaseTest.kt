package com.virtualworld.multiplatformiot.domain.conectionInternet.usecase

import com.virtualworld.multiplatformiot.data.core.ResponseStateData
import com.virtualworld.multiplatformiot.data.core.dto.ArduinoData
import com.virtualworld.multiplatformiot.data.core.dto.StateObject
import com.virtualworld.multiplatformiot.domain.conectionInternet.mapper.mapperToDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.model.ResponseStatesDomain
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import com.virtualworld.multiplatformiot.domain.core.models.ArduinoDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.BeforeTest

//+++++++++++++++ AQUÍ EMPIEZA LA IMPLEMENTACIÓN DEL FAKE +++++++++++++++

class FakeRepositoryInternet : RepositoryInternet {

    // 1. Variable para almacenar la respuesta que queremos simular
    private lateinit var response: ResponseStateData<List<ArduinoData>>

    // 2. Método para configurar la respuesta desde nuestros tests
    fun setAllArduinosResponse(response: ResponseStateData<List<ArduinoData>>) {
        this.response = response
    }

    // 3. Implementación del método de la interfaz
    override suspend fun getAllArduinos(): ResponseStateData<List<ArduinoData>> {
        // Simplemente devuelve la respuesta que hemos configurado
        return response
    }


    override fun getArduinos(name: String): Flow<ResponseStateData<ArduinoData>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateArduinoState(arduinoData: ArduinoData): ResponseStateData<StateObject> {
        TODO("Not yet implemented")
    }

    override suspend fun addArduino(arduino: ArduinoDomainModel) {
        TODO("Not yet implemented")
    }
}

class GetListArduinosUseCaseTest {

    private lateinit var getListArduinosUseCase: GetListArduinosUseCase
    private lateinit var fakeRepository: FakeRepositoryInternet // Un repositorio falso que tú creas para el test

    @BeforeTest
    fun setUp() {
        fakeRepository = FakeRepositoryInternet()
        getListArduinosUseCase = GetListArduinosUseCase(fakeRepository)
    }

    @Test
    fun `cuando el repositorio devuelve exito, el caso de uso devuelve exito con datos mapeados a dominio`() = runTest {
        // Preparamos los datos de la capa de DATOS (lo que el repo devuelve)
        val arduinoDataList = listOf(ArduinoData(nameArduino = "Arduino1", active = true))
        fakeRepository.setAllArduinosResponse(ResponseStateData.Success(arduinoDataList))

        //Acción (Act)
        val result = getListArduinosUseCase()

        // Verificación (Assert)
        assertTrue(result is ResponseStatesDomain.Success, "El resultado debería ser de tipo Success")

        // Creamos la lista esperada de la capa de DOMINIO para la comparación
        val expectedDomainList = arduinoDataList.map { it.mapperToDomain() }

        // Comparamos el resultado del UseCase con la lista de DOMINIO esperada
        assertEquals(expectedDomainList, (result as ResponseStatesDomain.Success).result)
    }

    @Test
    fun `cuando el repositorio devuelve error, el caso de uso devuelve el mismo error`() = runTest {
        // Preparación
        val exception = Exception("Error de red")
        fakeRepository.setAllArduinosResponse(ResponseStateData.Error(exception))

        // Acción (Act)
        val result = getListArduinosUseCase()

        // Verificación (Assert)
        assertTrue(result is ResponseStatesDomain.Error, "El resultado debería ser de tipo Error")
        assertEquals(exception, (result as ResponseStatesDomain.Error).exception)
    }


}
