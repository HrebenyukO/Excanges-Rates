package com.example.ExchangeRates.Service.SendTable;
import com.example.ExchangeRates.Service.Currency.MonoBankCurrencyBeanService;

import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.time.LocalDate;

import java.util.HashMap;

import java.util.Map;

@Component
public class CreateTable {
    @Autowired
    MonoBankCurrencyBeanService monoBankCurrencyBeanService;

    private HashMap<String, CurrencyOnlineDTO> lastCurrencyHashMap;

    public HashMap<String,CurrencyOnlineDTO> getLastCurrencyHashMap(){
        HashMap<String,CurrencyOnlineDTO> hashMap=new HashMap<>();
        var mono=monoBankCurrencyBeanService.createMonoDto();
        hashMap.put("МоноБанк",mono);


        return hashMap;
    }

    public byte[] createCurrencyTableBytes() {
        lastCurrencyHashMap = getLastCurrencyHashMap();
        int numRows = lastCurrencyHashMap.size() + 1; // +1 for header row
        int numCols = 3; // Bank, Dollar, Euro

        int cellWidth = 200; // Increased cell width
        int cellHeight = 60;
        int tableWidth = numCols * cellWidth;
        int tableHeight = numRows * cellHeight + 100;

        BufferedImage image = new BufferedImage(tableWidth, tableHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Make the table transparent
        image = g2d.getDeviceConfiguration().createCompatibleImage(tableWidth, tableHeight, Transparency.TRANSLUCENT);
        g2d = image.createGraphics();
        g2d.setColor(new Color(255, 255, 255, 0));
        g2d.fillRect(0, 0, tableWidth, tableHeight);

        // Draw headers
        g2d.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Курс валют " + LocalDate.now(), tableWidth / 2 - 120, 60);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2)); // Set a thicker line for headers
        g2d.drawLine(0, 100, tableWidth, 100); // Draw a thicker line for header row

        drawCell(g2d, "Bank", 0, 100, cellWidth, cellHeight);
        drawCell(g2d, "$", cellWidth, 100, cellWidth, cellHeight); // Dollar column
        drawCell(g2d, "€", 2 * cellWidth, 100, cellWidth, cellHeight); // Euro column

        // Draw data
        int i = 0;
        for (Map.Entry<String, CurrencyOnlineDTO> entry : lastCurrencyHashMap.entrySet()) {
            CurrencyOnlineDTO data = entry.getValue();
            drawCell(g2d, entry.getKey(), 0, (i + 1) * cellHeight + 100, cellWidth, cellHeight);

            // Combine dollar values with "/"
            String dollarValue = String.format("%.2f / %.2f", data.onlineDollarPurchase(), data.onlineDollarSales());
            drawCell(g2d, dollarValue, cellWidth, (i + 1) * cellHeight + 100, cellWidth, cellHeight);

            // Combine euro values with "/"
            String euroValue = String.format("%.2f / %.2f", data.onlineEuroPurchase(), data.onlineEuroSales());
            drawCell(g2d, euroValue, 2 * cellWidth, (i + 1) * cellHeight + 100, cellWidth, cellHeight);

            i++;
        }

        g2d.dispose();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void drawCell(Graphics2D g2d, String text, int x, int y, int width, int height) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1)); // Reset the line thickness
        g2d.drawRect(x, y, width, height);

        g2d.setColor(Color.BLACK);

        // Center-align text
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int xText = x + (width - textWidth) / 2;
        int yText = y + (height - textHeight) / 2 + fm.getAscent();

        g2d.drawString(text, xText, yText);
    }
}

