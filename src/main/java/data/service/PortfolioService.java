package data.service;

import data.dto.PortfolioDto;
import data.mapper.PortfolioMapperInter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioMapperInter portfolioMapper;
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    /**
     * Insert a new portfolio.
     *
     * @param dto the portfolio data transfer object
     */
    public void insertPortfolio(PortfolioDto dto) {
        try {
            portfolioMapper.insertPortfolio(dto);
            logger.info("Inserted portfolio with ID: {}", dto.getPortfolio_id());
        } catch (Exception e) {
            logger.error("Error inserting portfolio", e);
            throw e;
        }
    }

    /**
     * Retrieve portfolio data by ID.
     *
     * @param portfolio_id the portfolio ID
     * @return the portfolio data transfer object
     */
    public PortfolioDto portfolioData(int portfolio_id) {
        try {
            return portfolioMapper.portfolioData(portfolio_id);
        } catch (Exception e) {
            logger.error("Error retrieving portfolio data for ID: {}", portfolio_id, e);
            throw e;
        }
    }

    /**
     * Update an existing portfolio.
     *
     * @param dto the portfolio data transfer object
     */
    public void updatePortfolio(PortfolioDto dto) {
        try {
            portfolioMapper.updatePortfolio(dto);
            logger.info("Updated portfolio with ID: {}", dto.getPortfolio_id());
        } catch (Exception e) {
            logger.error("Error updating portfolio", e);
            throw e;
        }
    }

    /**
     * Delete a portfolio by ID.
     *
     * @param portfolio_id the portfolio ID
     */
    public void deletePortfolio(int portfolio_id) {
        try {
            portfolioMapper.deletePortfolio(portfolio_id);
            logger.info("Deleted portfolio with ID: {}", portfolio_id);
        } catch (Exception e) {
            logger.error("Error deleting portfolio with ID: {}", portfolio_id, e);
            throw e;
        }
    }
}
