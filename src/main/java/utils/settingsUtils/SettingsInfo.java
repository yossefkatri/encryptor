package utils.settingsUtils;


import com.fasterxml.jackson.annotation.JsonIgnore;
import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import encriptionAlgorithms.basicAlgorithms.ShiftMultiplyEncryption;
import encriptionAlgorithms.basicAlgorithms.ShiftUpEncryption;
import encriptionAlgorithms.basicAlgorithms.XorEncryption;
import encriptionAlgorithms.complexAlgorithm.DoubleEncryption;
import encriptionAlgorithms.complexAlgorithm.RepeatEncryption;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@XmlRootElement(name = "SettingInfo")
@XmlType(propOrder = {"choice","sourcePath", "outputPath","keyPath","nameEncryptionAlgorithm", "times"})
public class SettingsInfo<T> {
    int choice;
    int[] times;
    Path keyPath;
    @XmlElement(name = "inputPath")
    Path sourcePath;
    Path outputPath;
    @JsonIgnore
    @XmlTransient
    IEncryptionAlgorithm<T> encryptionAlgorithm;
    String nameEncryptionAlgorithm;

    public String getNameEncryptionAlgorithm() {
        return nameEncryptionAlgorithm;
    }
    public void setNameEncryptionAlgorithm(String nameEncryptionAlgorithm) {
        this.nameEncryptionAlgorithm = nameEncryptionAlgorithm;
    }

    public int[] getTimes() {
        return times;
    }
    public void setTimes(int[] times) {
        this.times = times;
    }

    public int getChoice() {
        return choice;
    }
    public void setChoice(int choice) {
        this.choice = choice;
    }

    public Path getKeyPath() {
        return keyPath;
    }
    public void setKeyPath(Path keyPath) {
        this.keyPath = keyPath;
    }

    public Path getSourcePath() {
        return sourcePath;
    }
    public void setSourcePath(Path sourcePath) {
        this.sourcePath = sourcePath;
    }

    public Path getOutputPath() {
        return outputPath;
    }
    public void setOutputPath(Path outputPath) {
        this.outputPath = outputPath;
    }

    public IEncryptionAlgorithm<T> getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }
    public void setEncryptionAlgorithm(IEncryptionAlgorithm<T> encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    //works only for integers encryption
    public void ToEncryptionAlgorithm() throws ClassNotFoundException {
        String encryptionName = nameEncryptionAlgorithm.replaceAll("\\{","").replaceAll("}","");
        List<String> tokens = Arrays.asList(encryptionName.split("Encryption"));
        Collections.reverse(tokens);
        EncryptionAlgorithmImpl<Integer> encryptionAlgorithm = null;

        int i = times.length - 1;
        for (String className :
                tokens) {
            switch (className)
            {
                case "Repeat":
                    assert encryptionAlgorithm != null;
                    encryptionAlgorithm = new RepeatEncryption<>(encryptionAlgorithm,times[i]);
                    --i;
                    break;
                case "Double":
                    assert encryptionAlgorithm != null;
                    encryptionAlgorithm = new DoubleEncryption<>(encryptionAlgorithm);
                    break;
                case "ShiftMultiply":
                    encryptionAlgorithm = ShiftMultiplyEncryption.getInstance();
                    break;
                case "ShiftUp":
                    encryptionAlgorithm = ShiftUpEncryption.getInstance();
                    break;
                case "Xor":
                    encryptionAlgorithm = XorEncryption.getInstance();
                    break;
            }
        }
        this.encryptionAlgorithm = (IEncryptionAlgorithm<T>) encryptionAlgorithm;
    }}