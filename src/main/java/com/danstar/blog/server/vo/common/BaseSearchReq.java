package com.danstar.blog.server.vo.common;

import com.danstar.blog.server.infrastructure.validation.group.SearchOperation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Schema(title = "查询请求基类")
@Data
public class BaseSearchReq {

    @Min(value = 0, message = "页码不能小于0", groups = SearchOperation.class)
    @NotNull(message = "页码不能为空", groups = SearchOperation.class)
    @Schema(description = "页码")
    private Integer page;

    @Max(value = 100, message = "每页大小不能大于100", groups = SearchOperation.class)
    @Min(value = 1, message = "每页大小不能小于1", groups = SearchOperation.class)
    @NotNull(message = "每页大小不能为空", groups = SearchOperation.class)
    @Schema(description = "每页大小")
    private Integer size;

    @Schema(description = "排序字段")
    private String sortField;

    @Pattern(regexp = "^(desc|asc)$", message = "排序方式只能为desc或asc", groups = SearchOperation.class)
    @Schema(description = "排序方式")
    private String sortMethod;

    // 获取排序，默认按照updateTime降序
    @JsonIgnore
    public Sort getSort() {
        // 如果排序字段和排序方法都未指定，使用默认值
        if (sortField == null && sortMethod == null) {
            return Sort.by(Sort.Direction.DESC, "updateTime");
        }

        // 如果只指定了排序方法，默认排序字段为"updateTime"
        if (sortField == null) {
            return Sort.by(Sort.Direction.fromString(sortMethod), "updateTime");
        }

        // 如果只指定了排序字段，默认排序方法为降序
        if (sortMethod == null) {
            return Sort.by(Sort.Direction.DESC, sortField);
        }

        // 如果都指定了，按照指定的排序字段和方法排序
        return Sort.by(Sort.Direction.fromString(sortMethod), sortField);
    }
}

