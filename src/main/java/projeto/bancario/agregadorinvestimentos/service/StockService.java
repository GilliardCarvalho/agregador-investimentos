package projeto.bancario.agregadorinvestimentos.service;


import org.springframework.stereotype.Service;
import projeto.bancario.agregadorinvestimentos.controller.dto.CreateStockDto;
import projeto.bancario.agregadorinvestimentos.entity.Stock;
import projeto.bancario.agregadorinvestimentos.repository.StockRepository;

@Service
public class StockService {

    private StockRepository stockRepository;


    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDTO) {
        //DTO ->
        var stock = new Stock(
            createStockDTO.stockId(),
                createStockDTO.description()
        );

        stockRepository.save(stock);
    }
}
