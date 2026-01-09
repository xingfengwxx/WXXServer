# API 测试脚本
Write-Host "=== 测试健康检查接口 ===" -ForegroundColor Green
try {
    $health = Invoke-RestMethod -Uri "http://localhost:8080/api/health/ping" -Method GET
    Write-Host "健康检查: $($health | ConvertTo-Json)" -ForegroundColor Cyan
} catch {
    Write-Host "健康检查失败: $_" -ForegroundColor Red
    exit 1
}

Write-Host "`n=== 测试获取用户接口 ===" -ForegroundColor Green
try {
    $user = Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method GET
    Write-Host "用户查询结果:" -ForegroundColor Cyan
    Write-Host ($user | ConvertTo-Json -Depth 5)
} catch {
    Write-Host "用户查询失败: $_" -ForegroundColor Red
    Write-Host "响应: $($_.Exception.Response)" -ForegroundColor Yellow
}

Write-Host "`n=== 测试完成 ===" -ForegroundColor Green

