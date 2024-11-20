package come.dawnlight.chronicle_dawnlight.service.impl;

import come.dawnlight.chronicle_dawnlight.mapper.ToolsMapper;
import come.dawnlight.chronicle_dawnlight.service.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolsServiceImpl implements ToolsService {
    @Autowired
    private ToolsMapper toolsMapper;

    @Override
    public String getTouhouUrl() {
        return toolsMapper.getTouhouUrl();
    }
}
