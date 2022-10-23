package com.metaheuristics.readers.fileoperation;

import com.metaheuristics.exceptions.LogicException;
import com.metaheuristics.simulation.model.Specimen;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Logger;

public class FileOperationImpl implements FileOperation {

    private final static String filePath = "Charts/data.csv";
    private static final DecimalFormat decimalFormat = new DecimalFormat("########.#");
    private final File file = new File(filePath);

    public FileOperationImpl() {
        try {
            Logger logger = Logger.getLogger(FileOperation.class.getSimpleName());
            if(!file.exists()) {
                logger.info("Create file");
                createFile();
            } else {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
                logger.info("Clear file");
            }
        } catch (Exception e) {
            throw new LogicException("Problem with File creation");
        }

    }

    private void createFile() throws IOException {
        file.createNewFile();
    }

    @Override
    public void writeData(int round, List<Specimen> generation) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("epoch,adaptation");
            bw.newLine();
            for (Specimen specimen : generation) {
                bw.write(round + "," + decimalFormat.format(specimen.getAdaptation()));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            throw new LogicException("error during collecting statistics");
        }
    }

}
