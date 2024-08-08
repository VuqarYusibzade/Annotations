package orm;

import az.code.annotations.ExcelColumn;
import az.code.annotations.ExcelSheet;
import az.code.model.Person;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManager {
    private Map<Class<?>, List<Object>> data = new HashMap<>();

    public void addEntitiesFromExcelFile(String filePath) {
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("people");

            for (Row row : sheet) {
//                if (row.getRowNum() == 0) {
//                    continue;
//                }
                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();

                Person person = new Person(id, name);
                addEntity(person);
            }

            workbook.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEntity(Object entity) {
        Class<?> clazz = entity.getClass();
        if (clazz.isAnnotationPresent(ExcelSheet.class)) {
            if (!data.containsKey(clazz)) {
                data.put(clazz, new ArrayList<>());
            }
            data.get(clazz).add(entity);
        }
    }

    public <T> T findById(Class<T> clazz, int id) {
        for (List<Object> entityList : data.values()) {
            for (Object obj : entityList) {
                if (clazz.isInstance(obj)) {
                    try {
                        java.lang.reflect.Field idField = obj.getClass().getDeclaredField("id");
                        idField.setAccessible(true);
                        int fieldValue = (int) idField.get(obj);
                        if (fieldValue == id) {
                            return clazz.cast(obj);
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }


    public <T> List<T> getAll(Class<T> clazz) {
        List<T> resultList = new ArrayList<>();
        for (List<Object> entityList : data.values()) {
            for (Object obj : entityList) {
                if (clazz.isInstance(obj)) {
                    resultList.add(clazz.cast(obj));
                }
            }
        }

        return resultList;
    }

    public Map<Class<?>, List<Object>> getData() {
        return data;
    }
}
