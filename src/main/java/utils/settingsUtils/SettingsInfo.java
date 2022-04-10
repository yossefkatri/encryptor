package utils.settingsUtils;


import com.fasterxml.jackson.annotation.JsonIgnore;
import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import encriptionAlgorithms.basicAlgorithms.ShiftMultiplyEncryption;
import encriptionAlgorithms.basicAlgorithms.ShiftUpEncryption;
import encriptionAlgorithms.basicAlgorithms.XorEncryption;
import encriptionAlgorithms.complexAlgorithm.DoubleEncryption;
import encriptionAlgorithms.complexAlgorithm.RepeatEncryption;
import utils.settingsUtils.jaxb.PathAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "SettingInfo")
@XmlType(propOrder = {"choice","sourcePath", "outputPath","keyPath","nameEncryptionAlgorithm", "times"})
public class SettingsInfo<T> {
    int choice;
    int[] times;
    Path keyPath;
    Path sourcePath;
    Path outputPath;
    @JsonIgnore
    IEncryptionAlgorithm<T> encryptionAlgorithm;
    String nameEncryptionAlgorithm;

    public String getNameEncryptionAlgorithm() {
        return nameEncryptionAlgorithm;
    }
    public void setNameEncryptionAlgorithm(String nameEncryptionAlgorithm) {
        this.nameEncryptionAlgorithm = nameEncryptionAlgorithm;
    }

    @XmlElementWrapper(name = "times")
    @XmlElement(name = "time")
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
    @XmlJavaTypeAdapter(PathAdapter.class)
    public void setKeyPath(Path keyPath) {
        this.keyPath = keyPath;
    }

    @XmlElement(name = "inputPath")
    public Path getSourcePath() {
        return sourcePath;
    }
    @XmlJavaTypeAdapter(PathAdapter.class)
    public void setSourcePath(Path sourcePath) {
        this.sourcePath = sourcePath;
    }

    public Path getOutputPath() {
        return outputPath;
    }
    @XmlJavaTypeAdapter(PathAdapter.class)
    public void setOutputPath(Path outputPath) {
        this.outputPath = outputPath;
    }

    @XmlTransient
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SettingsInfo)) return false;
        SettingsInfo<?> that = (SettingsInfo<?>) o;
        return choice == that.choice && Arrays.equals(times, that.times) && Objects.equals(keyPath, that.keyPath) && Objects.equals(sourcePath, that.sourcePath) && Objects.equals(outputPath, that.outputPath) && Objects.equals(nameEncryptionAlgorithm, that.nameEncryptionAlgorithm);
    }

}