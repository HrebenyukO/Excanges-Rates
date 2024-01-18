package com.example.ExchangeRates.Service.SendTable;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import com.example.ExchangeRates.Service.Currency.MonoBankCurrencyBeanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


@Component
public class CreateTable {
    @Autowired
    private MonoBankCurrencyBeanService monoBankCurrencyService;
    private byte[] imageToBytes(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            baos.close();
            return imageBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] sendLatestDataToTelegram() {
      return imageToBytes(tableToImage(createTable()));
    }


    private JTable createTable() {
        DefaultTableModel model = new DefaultTableModel();
        var data = monoBankCurrencyService.getLastOnlineDollar();
        model.addColumn("Банк");
        model.addColumn("Доллар Купівля");
        model.addColumn("Доллар Продажа");
        model.addColumn("Дата");

        // Используем данные из объекта OnlineDollarMonobank
        model.addRow(new Object[]{
                data.getBank(),
                data.getOnlinePurchaseDollar(),
                data.getOnlineSaleDollar(),
                new SimpleDateFormat("dd/MM/yyyy").format(data.getDate())
        });

        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true); // Включаем сортировку

        // Оборачиваем таблицу в JScrollPane для отображения заголовка
        JScrollPane scrollPane = new JScrollPane(table);

        return table;
    }

       /* // Используем данные из объекта OnlineDollarMonobank
        model.addRow(new Object[]{
                data.getBank(),
                data.getOnlinePurchaseDollar(),
                data.getOnlineSaleDollar(),
                new SimpleDateFormat("dd/MM/yyyy").format(data.getDate())
        });
        model.addRow(new Object[]{
                data.getBank(),
                data.getOnlinePurchaseDollar(),
                data.getOnlineSaleDollar(),
                new SimpleDateFormat("dd/MM/yyyy").format(data.getDate())
        });*/



    private BufferedImage tableToImage(JTable table) {
        int width = 300;
        int height=300; /*= table.getPreferredSize().height + table.getRowHeight();*/ // Увеличиваем высоту на высоту строки
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        table.setSize(table.getPreferredSize());  // Устанавливаем размер таблицы
        table.paint(g);
        return image;
    }


}