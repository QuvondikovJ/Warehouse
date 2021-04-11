package pdp.uz.program_41.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdp.uz.program_41.payload.CategoryDto;
import pdp.uz.program_41.payload.Result;
import pdp.uz.program_41.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
@Autowired
CategoryService categoryService;

@PostMapping
    public Result add(@RequestBody CategoryDto categoryDto){
    return categoryService.add(categoryDto);
}
@GetMapping
    public Result get(){
    return categoryService.get();
}
@GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
    return categoryService.getById(id);
}
@PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody CategoryDto categoryDto){
    return categoryService.edit(id,categoryDto);
}
@DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
    return categoryService.delete(id);
}
}
